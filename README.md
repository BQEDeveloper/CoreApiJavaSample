# Java Sample App
A sample app demonstrating OAuth 2.0 and other features using Core API.

## Getting Started

  1. Clone the CoreAPIJavaSample project on your local environment.
  2. Go to Config.ini and insert the client_secret, client_id and redirect_uri of your app. Please note the redirect_uri should point to the index.jsp file of the project.
  #### Note: 
  The responses during the OAuth process, including access_token, referesh_token, etc., are stored in the AuthResponse.ini file. As a best practice, you should protect this information by storing it in a database and use secure methods to access it.
  
As an example, if you are running Java on your localhost with port 8080, the redirect_uri will look like
http://localhost:8080/CoreApiJavaSample/. Note: The redirect_uri of your app should exactly match with the redirect_uri in your config file.
  ### Example:

  | Registered Redirect URI| Redirect URI Parameter Passed To Authorize| Valid |
  |------------------------|--------------------------------------------|--    |
  |http://yourcallback.com/|http://yourcallback.com                     |No    |
  |http://yourcallback.com/|http://yourcallback.com/                    |Yes   |
     
  3. Run the project. 

### Requirements

To successfully run this app, you need the following:

  * A Core [developer](https://api-developer.bqecore.com/webapp) account
  * An app on Developer Portal and the associated client_id, client_secret and redirect_uri
  * Core company
  * A Java platform or [JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html), and a Java environment comprising of [Tomcat](https://tomcat.apache.org/).

### What is supported?
  1. Authorization 
  2. Authentication
  3. Activity - Retrieve, Create, Update and Delete

### Querying
We allow the following simple filters on different endpoints:

  * fields - To specify only those model properties which you want in the response body
  * where -  To specify only those records that match the query expression
  * orderBy - To specify by which field you want to order the item list
  * page -  To specify the page number and number of records on each page

Core API allows operators to manipulate individual data items and return a result set. To know more go to [Core Operators](https://api-explorer.bqecore.com/docs/filtering#filter-operators)

## Built With

  * [Java](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
  
## Framework and Version
    JSP and Servlets
  
  | Package| Version|
  |------------------------|--------------------------------------------|
  |JRE|1.8.0_181                    |
