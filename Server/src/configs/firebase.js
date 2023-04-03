import admin from 'firebase-admin'

// Cấu hình tài khoản dịch vụ (service account)
const serviceAccount = require('./app-lap-top-kotlin-e2b1e564cac5.json');

// Khởi tạo Firebase Admin SDK với thông tin cấu hình
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: 'https://app-lap-top-kotlin-default-rtdb.firebaseio.com/',
    storageBucket: 'app-lap-top-kotlin.appspot.com'
});

// Sử dụng các tính năng của Firebase Admin SDK
// Ví dụ: lấy tham chiếu đến Realtime Database
const database = admin.database();
// Ví dụ: lấy tham chiếu đến Storage
const storage = admin.storage().bucket();

// Xuất các tham chiếu tới các tính năng của Firebase Admin SDK
export {
    database,
    storage
};
