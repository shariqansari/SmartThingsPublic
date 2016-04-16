/**
 *  NX-595e Alarm Module Zone
 *
 *  Copyright 2016 Trent Foley
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "NX-595e Zone", namespace: "trentfoley", author: "Trent Foley") {
		capability "Actuator"
        capability "Contact Sensor"
		capability "Switch"
        capability "Sensor"

		attribute "zoneIndex", "number"
		attribute "status", "string"
        
        command "setZoneIndex", ["number"]
        command "setStatus", ["string"]
	}

	tiles(scale: 2) {
		multiAttributeTile(name:"zone", type:"generic", width: 6, height: 4) {
        	tileAttribute("device.status", key: "PRIMARY_CONTROL") {
            	attributeState "Ready", label: '${name}', icon: "st.contact.contact.closed", backgroundColor: "#79b821"
                attributeState "Not Ready", label: '${name}', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
                attributeState "Tamper", label: '${name}', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
                attributeState "Trouble", label: '${name}', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
                attributeState "Bypass", label: '${name}', icon: "st.contact.contact.open", backgroundColor: "#ffffff"
                attributeState "Inhibited", label: '${name}', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
                attributeState "Alarm", label: '${name}', icon: "st.alarm.alarm.alarm", backgroundColor: "#ffa81e"
                attributeState "Low Battery", label: '${name}', icon: "st.contact.contact.closed", backgroundColor: "#ffa81e"
                attributeState "Supervision Fault", label: '${name}', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
            }
		}
        valueTile("zoneIndex", "device.zoneIndex", decoration: "flat", width: 3, height: 3) {
            state "zoneIndex", label: 'Zone ${currentValue}'
        }
        standardTile("bypass", "device.switch", width: 3, height: 3, canChangeIcon: true) {
			state "off", label: 'Bypass', action: "switch.on", backgroundColor: "#ffffff", nextState: "on"
			state "on", label: 'Bypass', action: "switch.off", backgroundColor: "#ffa81e", nextState: "off"
		}
        main "zone"
		details(["zone", "zoneIndex", "bypass"])
	}
}

def setZoneIndex(zoneIndex) {
	sendEvent(name: "zoneIndex", value: zoneIndex)
}

def setStatus(status) {
	log.debug "setStatus(${status})"
	
    def contactValue = status == "Ready" ? "closed" : "open"
    
    sendEvent(name: "status", value: status)
    sendEvent(name: "contact", value: contactValue, displayed: false)
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

def on() {
	sendEvent(name: "switch", value: "on")
}

def off() {
	sendEvent(name: "switch", value: "off")
}
