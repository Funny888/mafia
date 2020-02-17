const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.SendCommonMessage = functions.https.onRequest(async (request, response) => {
   
    const id = admin.firestore().collection("/Rooms/CommonChat/Messages").listDocuments();
    id.then(
        result => {
            const doc = admin.firestore().doc("/Rooms/CommonChat/Messages/Message" + (result.length + 1));
            doc.create(request.body);},
        error => {response.send("crash => " + error)}
        );
});