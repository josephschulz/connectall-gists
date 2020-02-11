/**
 * ConnectALL Custom Business Rule Template
 * 
 * This is a template for a ConnectALL custom business rule.  It includes the basic logic to
 * filter messages based on specified criteria.  It also includes an empty stub method that 
 * should be used to add custom logic.
 * 
 * @author Joe Schulz
 * @version 1.1
 * @date 02/10/2020
 * 
 */

///////////////////////////////////////////////////////////////////////
// Set the filter criteria
//
//   appLinkName   - specifies the ConnectALL display name for the application link object
//                   e.g. "ServiceNowIncident2JiraBug"
//   destURL       - specifies the fully qualified URL of the target server
//                   e.g. "http://myjira.com"
//   sourceLinkID  - specifies the fully qualified ConnectALL Source Link ID
//                   e.g. "ServiceNowIncident2JiraBug_1856766403"
//   destLinkID    - specifies the fully qualified ConnectALL Destination Link ID
//                   e.g. "ServiceNowIncident2JiraBug_1333124748"
//   destProject   - specifies the destination Project Name
//                   e.g. "MyProjectName"
//   destIssueType - specifies the destination Object Type ID
//                   e.g. "10102"
//
// For each criteria, specify a single value in quotes or the word "all"
//
///////////////////////////////////////////////////////////////////////
String appLinkName = "all";
String destURL = "all";
String sourceLinkID = "all";
String destLinkID = "all";
String destProject = "all";
String destIssueType = "all";

///////////////////////////////////////////////////////////////////////
// Set the debug properties
///////////////////////////////////////////////////////////////////////
boolean showPayloadDetail = false

println "*******************************************************************************";
println "BEGIN BusinessRuleTemplate.groovy";
println "*******************************************************************************";
println "Executing custom script: BusinessRuleTemplate.groovy";

// Check whether the current message matches the filter criteria
printPayload(message, "ORIGINAL", showPayloadDetail);
if (matchingMessage(message, appLinkName, destURL, sourceLinkID, destLinkID, destProject, destIssueType))
{
	doSomething(message);
	printPayload(message, "UPDATED", showPayloadDetail);
}

println "*******************************************************************************";
println "END BusinessRuleTemplate.groovy";
println "*******************************************************************************";

return message.payload;

/**
 * Custom Logic in this method
 *
 */
def doSomething(message)
{
	///////////////////////////////////////////////////////////////////////
	// ADD CUSTOM LOGIC BELOW
	///////////////////////////////////////////////////////////////////////
	// Set the new value in the source field
	//def oldValue = message.payload.getSingleValueField("XXX");
	//println "Field XXX old value: " + oldValue;
	//def newValue = "YYY";
	//message.payload.setSingleValueField("XXX", newValue);
	//println "Field XXX changed to: " + message.payload.getSingleValueField("XXX");

	println "No logic defined, default mappings used";
}

///////////////////////////////////////////////////////////////////////
// NO NEED TO CHANGE ANYTHING BELOW THIS POINT
///////////////////////////////////////////////////////////////////////

/**
 * Check for matching messages
 *
 * This function determines whether the current message meets the specified filtering criteria.
 * If so, it returns a value of true.  Otherwise, it returns a value of false.
 *
 * @returns boolean
 *
 */
def matchingMessage(message, String appLinkName, String destURL, String sourceLinkID,
		String destLinkID, String destProject, String destIssueType)
{
	// Inspect the message header properties
	println "Looking for AppLink: " + appLinkName;
	String result = message.getSessionProperty('connection.name').trim();
	println "Found AppLink: " + result;

	// Check whether the desired application link was found
	if (appLinkName.trim().equalsIgnoreCase(result) || appLinkName.trim().equalsIgnoreCase("all"))
	{
		println "Looking for URL: " + destURL;
		result = message.getSessionProperty('url').trim();
		println "Found URL: " + result;

		// Check whether the desired URL was found
		if (destURL.trim().equalsIgnoreCase(result) || destURL.trim().equalsIgnoreCase("all"))
		{
			println "Looking for Source Link ID: " + sourceLinkID;
			result = message.getSessionProperty("source").trim();
			println "Found Source Link ID: " + result;

			// Check whether the desired source project was found
			if (sourceLinkID.trim().equalsIgnoreCase(result) || sourceLinkID.trim().equalsIgnoreCase("all"))
			{
				println "Looking for Destination Link ID: " + destLinkID;
				result = message.getSessionProperty("destination").trim();
				println "Found Destination Link ID: " + result;

				// Check whether the desired source project was found
				if (destLinkID.trim().equalsIgnoreCase(result) || destLinkID.trim().equalsIgnoreCase("all"))
				{
					println "Looking for Destination Project: " + destProject;
					result = message.getSessionProperty("destination.project").trim();
					println "Found Destination Project: " + result;

					// Check whether the desired destination project was found
					if (destProject.trim().equalsIgnoreCase(result) || destProject.trim().equalsIgnoreCase("all"))
					{
						println "Looking for Issue Type: " + destIssueType;
						result = message.getSessionProperty("destination.issuetype").trim();
						println "Found Issue Type: " + result;

						// Check whether the desired entity was found
						if (destIssueType.trim().equalsIgnoreCase(result) || destIssueType.trim().equalsIgnoreCase("all"))
						{
							println "Message matches filter criteria";
							return true;
						}
					}
				}
			}
		}
	}

	println "No match, default mapping rules used";
	return false;
}

/**
 * Print Message Payload
 *
 * This function prints the contents of the current message and payload.
 *
 */
def printPayload(message, String payloadState, boolean showPayloadDetail)
{
	// Print the payload detail to the log
	if (showPayloadDetail) 
	{ 
		println "--- START " + payloadState.trim().toUpperCase() + " PAYLOAD -----------------------------------------------";
		println "Processing message: " + message;
		println "Processing message payload: " + message.payload;
		println "--- END " + payloadState.trim().toUpperCase() + " PAYLOAD -------------------------------------------------";
	}
}
