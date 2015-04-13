/*
 * This file is part of Bootlace.
 *
 * Copyright (C) 2015
 * Caprica Software Limited (capricasoftware.co.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.caprica.bootlace.domain;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import uk.co.caprica.bootlace.data.RepositoryConfiguration;
import uk.co.caprica.bootlace.data.UsernameAuditor;

import com.google.common.base.MoreObjects.ToStringHelper;

/**
 * Base implementation for an auditable entity stored in the repository.
 * <p>
 * This base component provides the standard {@link CreatedBy}, {@link CreatedDate},
 * {@link LastModifiedBy}, {@link LastModifiedDate} and {@link Version} properties.
 * <p>
 * The audit fields are populated by a presence of the {@link EnableMongoAuditing} annotation on a
 * configuration class, and an implementation of an {@link AuditorAware} component.
 *
 * @see RepositoryConfiguration
 * @see UsernameAuditor
 */
public abstract class AbstractAuditableEntity extends AbstractEntity {

    /**
     * Standard property for the username of the user that created the entity.
     */
    @CreatedBy
    private String createdBy;

    /**
     * Standard property for the timestamp when the entity was created.
     */
    @CreatedDate
    private Date createdDate;

    /**
     * Standard property for the username of the user that last modified the entity.
     */
    @LastModifiedBy
    private String lastModifiedBy;

    /**
     * Standard property for the timestamp when the entity was last modified.
     */
    @LastModifiedDate
    private Date lastModifiedDate;

    /**
     * Standard property for the version of the entity.
     * <p>
     * The version number is increased each time the entity is saved.
     */
    @Version
    private Long version;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    protected ToStringHelper toStringHelper() {
        return super.toStringHelper()
            .add("createdBy", createdBy)
            .add("createdDate", createdDate)
            .add("lastModifiedBy", lastModifiedBy)
            .add("lastModifiedDate", lastModifiedDate)
            .add("version", version);
    }

}
