/**
 *  Sebastian's Scene Settter-Child
 *
 *  Version 1.0.0 (12/12/15) - Initial release of child app
 * 
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
 
definition(
    name: "Sebastian's Scene Setter-Scene",
    namespace: "MichaelStruck",
    author: "Michael Struck",
    description: "Child Apps-Control multiple scenes, which can control multiple colored bulbs",
    category: "Convenience",
    iconUrl: "https://raw.githubusercontent.com/MichaelStruck/SmartThings/master/Other-SmartApps/Lighting-Director/LightingDirector.png",
    iconX2Url: "https://raw.githubusercontent.com/MichaelStruck/SmartThings/master/Other-SmartApps/Lighting-Director/LightingDirector@2x.png",
    iconX3Url: "https://raw.githubusercontent.com/MichaelStruck/SmartThings/master/Other-SmartApps/Lighting-Director/LightingDirector@2x.png")

preferences {
    page name:"pageSetup"
}

// Show setup page
def pageSetup() {
	dynamicPage(name: "pageSetup", install: true, uninstall: true) {
		section("Name your scenario") {
			label title:"Scenario Name", required: true
    	}
    	section {
        	input "A_vSwitch", "capability.switch",title: "Monitor this switch", multiple: true, required: false
        }
        section ("Colored Light Settings"){	
            input "A_switches", "capability.colorControl",title: "Toggle the following switches with above switch", multiple: true, required: false
        	input "A_hue", "num", title: "Set the hue", required: false
            input "A_sat", "num", title: "Set the saturartion", required: false
            input "A_level", "num", title: "Set the level", required: false
        }
 	    section("Restrictions") {            
			input name: "A_mode", type: "mode", title: "Only during the following modes...", multiple: true, required: false
		}
        section("About ${textAppName()}") { 
			paragraph "${textVersion()}\n${textCopyright()}"
    	}
    }
}

//----------------------
def installed() {
    initialize()
}

def updated() {
    unschedule()
    unsubscribe()
    initialize()
}

def initialize() {
	subscribe(A_vSwitch, "switch", A_colorHandler)
}
// A Events
def A_colorHandler(evt) {
	if (evt.value == "on"){
        def dimLevel = A_level as int
    	def hueLevel = A_hue as int
    	def saturationLevel = A_sat as int
        def newValue = [hue: hueLevel, saturation: saturationLevel, level: dimLevel as Integer]
        A_switches?.setColor(newValue)
    }
    if (evt.value =="off"){
    	A_switches?.off()
    }

}

//Version/Copyright

private def textAppName() {
	def text = "Sebastian's Scene Settter-Scene"
}	

private def textVersion() {
    def text = "Version 1.0.0 (12/12/2015)"
}

private def textCopyright() {
    def text = "Copyright © 2015 Michael Struck"
}