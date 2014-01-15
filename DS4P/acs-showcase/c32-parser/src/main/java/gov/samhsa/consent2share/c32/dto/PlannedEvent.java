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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.18 at 11:05:26 AM EDT 
//


package gov.samhsa.consent2share.c32.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


// TODO: Auto-generated Javadoc
/**
 * <p>Java class for plannedEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="plannedEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="planId" type="{urn:hl7-org:v3}ii"/>
 *         &lt;element name="plannedTime" type="{urn:hl7-org:v3}ivlTs" minOccurs="0"/>
 *         &lt;element name="planType" type="{urn:hl7-org:v3}cd"/>
 *         &lt;element name="planFreeText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "plannedEvent", propOrder = {
    "planId",
    "plannedTime",
    "planType",
    "planFreeText"
})
public class PlannedEvent {

    /** The plan id. */
    @XmlElement(required = true)
    protected Ii planId;
    
    /** The planned time. */
    protected IvlTs plannedTime;
    
    /** The plan type. */
    @XmlElement(required = true)
    protected Cd planType;
    
    /** The plan free text. */
    protected String planFreeText;

    /**
     * Gets the value of the planId property.
     *
     * @return the plan id
     * possible object is
     * {@link Ii }
     */
    public Ii getPlanId() {
        return planId;
    }

    /**
     * Sets the value of the planId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ii }
     *     
     */
    public void setPlanId(Ii value) {
        this.planId = value;
    }

    /**
     * Gets the value of the plannedTime property.
     *
     * @return the planned time
     * possible object is
     * {@link IvlTs }
     */
    public IvlTs getPlannedTime() {
        return plannedTime;
    }

    /**
     * Sets the value of the plannedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvlTs }
     *     
     */
    public void setPlannedTime(IvlTs value) {
        this.plannedTime = value;
    }

    /**
     * Gets the value of the planType property.
     *
     * @return the plan type
     * possible object is
     * {@link Cd }
     */
    public Cd getPlanType() {
        return planType;
    }

    /**
     * Sets the value of the planType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cd }
     *     
     */
    public void setPlanType(Cd value) {
        this.planType = value;
    }

    /**
     * Gets the value of the planFreeText property.
     *
     * @return the plan free text
     * possible object is
     * {@link String }
     */
    public String getPlanFreeText() {
        return planFreeText;
    }

    /**
     * Sets the value of the planFreeText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanFreeText(String value) {
        this.planFreeText = value;
    }

}
