/* *************************************
* Author: Sharath
* Title: concat-business-rule.groovy
* Description: Prefix's the summary with a string before synchronizing the records; when synchronizing 
* back the updates from destination project, will remove the prefix and synchronize the delta only.
* The &lt;ScriptingComponent&gt; fro this business rule should added before &lt;transformer ref="MapValues" /&gt;
* Date: 2020-03-10
* Version: 1.0
* ************************************* */


println "Applying Custom Business Rules";

// Application Link Name (Case Sensitive)
def CONCAT_CONSTANT = "CLONE - "
def appLinkToApplyRule = ["applink1","applink2","applink3"];
def appLinkBeingProcessed = message.getSessionProperty('connection.name')
def destProject = message.getSessionProperty('destination.project')
dev mappedSrcProject = "CA"
dev mappedDestProject = "CAPROD"

// Check to see if the rule is to be applied
if (appLinkToApplyRule.contains(appLinkBeingProcessed)) {
    def summary = message.payload.getSingleValueField("Summary");

    // If destination is mapped source project prefix the summary otherwise replace the constant from summary
    if (mappedSrcProject.equalsIgnoreCase(destProject)) {

        if(summary!=null && !summary.trim().toUpperCase().startsWith(CONCAT_CONSTANT)) {
                println "Applying the Summary concatenation rule for record : "+message.payload.getRecordId();
                summary = CONCAT_CONSTANT + summary;
        }
    // Else if destination is mapped destination project then replace the prefixed Constats
    } else if (mappedDestProject.equalsIgnoreCase(destProject)) {

        if(summary!=null && summary.trim().toUpperCase().startsWith(CONCAT_CONSTANT)) {
                println "Applying the Summary replacement rule for record : "+message.payload.getRecordId();
                summary = summary.replaceFirst(CONCAT_CONSTANT,"")
        }

    }

    message.payload.setSingleValueField("Summary",summary);

} else {
  println "Skip processing for applink '"+appLinkBeingProcessed+"'"
}

println "Business Rules transformation completed";

return message.payload;
