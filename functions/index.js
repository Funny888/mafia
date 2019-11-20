functions = require('firebase-functions');
admin = require('firebase-admin');
admin.initializeApp()

exports.getMessage = functions.https.onRequest(async (request,response) =>{
admin.firestore().collection('/Rooms/CommonChat/Messages').get().
	then(snap => {
  var myArray = [];
	snap.docs.forEach(doc => {
      myArray.push(doc.data());});
	response.send(myArray);}).
	catch((error) => {response.status(500).send(error)});
});
