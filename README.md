Project Name - User-Authenticatior(An APi to Authenticate the user)
used Techenology - Java(Spring-Boot,Spring Security), Mysql Database

System Requirment - 
Plateform - Window/Linux (To Linux curl would be modify)
Jdk  - 17 ( We have used Spring security v3.2.1 which is compitable with jdk 17 or upper )
Mysql - 5.6+

------------------------------- ABOUT FEATURES -------------
About Implemented Feature:
1. We can create User/Signup wing email and password
2. Login the user using email and password and generate the Acces-Token and Refresh-Token
	Acces-Token- It will be use to acces all the protected resources 
	Refresh-Token - It will be use to generate Again Acces-Token only it has own Expiry Time
3. If Acces-Token got expire then we can regenrate using Refresh-Token 	
3. Can see current user details
4. User can fetch all users list
5. User can remove/delete any user
6. user can revoke any Acces-Token it can be possible from backend also

Note : this is a master branch so please use this command to make clone - git clone -b master 

Future Implementation:
We can parameterized the expiry time for both tokens in database
Implement email verification during the signup process to ensure valid user registrations.
Allow users to reset their password securely through a password reset mechanism.

------------------------- Instructions TO run the Application ----------------------------
step 1:
Please Install jdk 17 ( Download from reposatory ) Links-> https://drive.google.com/drive/folders/110UaTJzFGdQaoFJ7yka6SFi2C7-5pc3i?usp=sharing
step 2: change downloaded directory in startApplication.bat file.
Step 3: Now Application is ready to Start just dubble click on bat file.


----------------------- End-Points and their curl ----------------
--- Public resources ---
Create/Signup user
curl --location --request POST "http://localhost:8080/auth/signup" --header "Content-Type: application/json" --data "{\"email\":\"Arjun\",\"password\":\"a\"}"

Login - It will retrun Acces-Token, Refresh-Token and user, Keep Refresh-Token safe to regenerate the acces token after expiry
curl --location --request POST "http://localhost:8080/auth/login" --header "Content-Type: application/json" --data "{\"email\":\"Arjun\",\"password\":\"a\"}"

Refresh Token - It is only useable for generating ACCES_TOKEN 
curl --location --request POST "http://localhost:8080/auth/refresh" --header "Content-Type: application/json" --data "{\"refreshToken\":\"ab2bf0f8-4e17-4a07-8627-a7e175918a58\"}"

--- protected resources ----
See Current User
curl --location --request GET "http://localhost:8080/user/profile" --header "Authorization: ACCES_TOKEN"

see all user 
curl --location --request GET "http://localhost:8080/user/all" --header "Authorization: ACCES_TOKEN"

Remove Any User 
curl --location --request POST "http://localhost:8080/user/remove" --header "Authorization: ACCES_TOKEN" --header "Content-Type: application/json" --data "{\"email\":\"dds\"}"


Revoke Acces-Token -- Only Authorized user can Revoke the Acces-Token
curl --location --request POST "http://localhost:8080/user/revoke" --header "Authorization: ACCES_TOKEN" --header "Content-Type: application/json" --data "{\"accessToken\":\"YOUR_ACCESS_TOKEN_HERE\"}"












