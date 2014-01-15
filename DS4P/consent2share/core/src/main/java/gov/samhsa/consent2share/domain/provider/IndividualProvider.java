/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.domain.provider;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * The Class IndividualProvider.
 */
@Entity
@Audited(auditParents=AbstractProvider.class)
@AuditTable("Individual_Provider_audit")
@SequenceGenerator(name="idgen", sequenceName="INDPROV_SEQ", initialValue = 1)
public class IndividualProvider extends AbstractProvider {

    /** The last name. */
    @NotNull
    @Size(max = 30)
    private String lastName;

    /** The first name. */
    @NotNull
    @Size(max = 30)
    private String firstName;

    /** The middle name. */
    @NotNull
    @Size(max = 30)
    private String middleName;

    /** The name prefix. */
    @NotNull
    @Size(max = 30)
    private String namePrefix;

    /** The name suffix. */
    @NotNull
    @Size(max = 30)
    private String nameSuffix;

    /** The credential. */
    @NotNull
    @Size(max = 30)
    private String credential;
    
	/**
	 * To json.
	 *
	 * @return the string
	 */
	public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

	/**
	 * From json to individual provider.
	 *
	 * @param json the json
	 * @return the individual provider
	 */
	public static IndividualProvider fromJsonToIndividualProvider(String json) {
        return new JSONDeserializer<IndividualProvider>().use(null, IndividualProvider.class).deserialize(json);
    }

	/**
	 * To json array.
	 *
	 * @param collection the collection
	 * @return the string
	 */
	public static String toJsonArray(Collection<IndividualProvider> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	/**
	 * From json array to individual providers.
	 *
	 * @param json the json
	 * @return the collection
	 */
	public static Collection<IndividualProvider> fromJsonArrayToIndividualProviders(String json) {
        return new JSONDeserializer<List<IndividualProvider>>().use(null, ArrayList.class).use("values", IndividualProvider.class).deserialize(json);
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
        return this.lastName;
    }

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
        return this.firstName;
    }

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

	/**
	 * Gets the middle name.
	 *
	 * @return the middle name
	 */
	public String getMiddleName() {
        return this.middleName;
    }

	/**
	 * Sets the middle name.
	 *
	 * @param middleName the new middle name
	 */
	public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

	/**
	 * Gets the name prefix.
	 *
	 * @return the name prefix
	 */
	public String getNamePrefix() {
        return this.namePrefix;
    }

	/**
	 * Sets the name prefix.
	 *
	 * @param namePrefix the new name prefix
	 */
	public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

	/**
	 * Gets the name suffix.
	 *
	 * @return the name suffix
	 */
	public String getNameSuffix() {
        return this.nameSuffix;
    }

	/**
	 * Sets the name suffix.
	 *
	 * @param nameSuffix the new name suffix
	 */
	public void setNameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

	/**
	 * Gets the credential.
	 *
	 * @return the credential
	 */
	public String getCredential() {
        return this.credential;
    }

	/**
	 * Sets the credential.
	 *
	 * @param credential the new credential
	 */
	public void setCredential(String credential) {
        this.credential = credential;
    }

}
