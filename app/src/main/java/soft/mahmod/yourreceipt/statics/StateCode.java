package soft.mahmod.yourreceipt.statics;


public interface StateCode  {

    int VERIFY = 201;
    int SUCCESS = 200;
    int ACTIVE = 202;
    int WEAK_PASSWORD = 203;
    int NUMBER_SECURITY = 204;

    int TRY_AGAIN = 400;
    int NOT_FOUND = 404;

    int UNKNOWN = 500;
    int PUBLIC_ERROR = 501;

    int PATH_NOT_EXISTS = 405;

//    ERROR_UNKNOWN	An unknown error occurred.
//    ERROR_OBJECT_NOT_FOUND	No object exists at the desired reference.
//    ERROR_BUCKET_NOT_FOUND	No bucket is configured for Cloud Storage
//    ERROR_PROJECT_NOT_FOUND	No project is configured for Cloud Storage
//    ERROR_QUOTA_EXCEEDED	Quota on your Cloud Storage bucket has been exceeded. If you're on the free tier, upgrade to a paid plan. If you're on a paid plan, reach out to Firebase support.
//    ERROR_NOT_AUTHENTICATED	User is unauthenticated, please authenticate and try again.
//    ERROR_NOT_AUTHORIZED	User is not authorized to perform the desired action, check your rules to ensure they are correct.
//    ERROR_RETRY_LIMIT_EXCEEDED	The maximum time limit on an operation (upload, download, delete, etc.) has been excceded. Try again.
//    ERROR_INVALID_CHECKSUM	File on the client does not match the checksum of the file received by the server. Try uploading again.
//    ERROR_CANCELED	User canceled the operation.

}
