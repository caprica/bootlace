'use strict';

/**
 * Controller for the Edit Profile form.
 * 
 * @param profile profile resource, provided by the resolver
 * @param Restangular 
 * @param toastr
 */
ProfileController.$inject = ['profile', 'Restangular', 'toastr'];
function ProfileController(profile, Restangular, toastr) {

    // Use the view-model pattern
    var vm = this;

    vm.reset       = Reset;
    vm.isUnchanged = IsUnchanged;
    vm.save        = Save;

    // Store the master model so that it can be restored later if "reset" is used
    vm.master = profile;

    /**
     * Reset the associated form to a pristine, untouched condition.
     * 
     * Note that initially there is no associated form instance.
     */
    function resetForm() {
        if (vm.form) {
            vm.form.$setPristine();
            vm.form.$setUntouched();
        }
    };

    /**
     * Reset the model.
     * 
     * The model values are overwritten by copying the original master model.
     */
    function Reset() {
        vm.model = angular.copy(vm.master);
        resetForm();
    };

    /**
     * Check if the current form model different to the master model.
     * 
     * @return true only if the form is unchanged
     */
    function IsUnchanged() {
        return angular.equals(vm.model, vm.master);
    };

    /**
     * Save the profile.
     * 
     * For this particular save method there is a special case...
     * 
     * The user profile is accessed at the "/profile" URL - this only ever returns the profile
     * resource for the currently authenticated user - the profile for a particular user can not be
     * retrieved or saved by that user's unique identifier.
     * 
     * Ordinarily it would suffice to simply invoke ".put()" directly on the model object that was
     * retrieved previously from "/profile". However, that model object contains an "id" property
     * so the PUT would actually go to the (non-existent) "/profile/{id}" resource, so a custom PUT
     * is required. 
     */
    function Save() {
        Restangular.one('profile').customPUT(vm.model).then(
            function onSuccess(updatedModel) {
                toastr.success('Profile saved');
                // Replace the master model with the new one returned from the web-service - it
                // will have updated properties (like version number)
                vm.master = updatedModel;
                vm.reset();
            },
            function onError() {
                toastr.error('Profile failed to save');
            }
        );
    };

    // Prepare the initial form model
    vm.reset();

};

/**
 * Implementation of a resolver that provides the current user profile from the web-service. 
 */
ProfileController.resolve = {

    profile: ['Restangular', function(Restangular) {
        return Restangular.one('profile').get();
    }]

};

module.exports = ProfileController;
