import express from 'express'
import multer from 'multer'
import path from 'path'
import appRoot from 'app-root-path'
import {
    getFormLogin,
    getFormRegister,
    getHomePage,
    handleRegister,
    handleLogin,
    getProductPage,
    getUltrabookPage,
    getGamingPage,
    getMacBookPage,
    getCreateProductPage,
    handleCreateProduct,
    getDetailsProductPage,
    getUpdateProductPage,
    handleUpdateProduct,
    handleDeleteProduct,
    getUserPage,
    getDetailsAdminPage,
    getEditAdminPage,
    handleUpdateAdmin,
    getDetailsUserPage,
    getEditUserPage,
    handleUpdateUser,
    handleDeleteAdmin,
    handleDeleteUser,
    getOrderPage,
    getOrderDetails,
    confirmOrder
} from '../controllers/web'

let route = express.Router()

// ============ middleware up load file ==============
const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, `${appRoot}/src/public/images/`);
    },

    // By default, multer removes file extensions so let's add them back
    filename: function (req, file, cb) {
        cb(null, file.fieldname + '-' + Date.now() + path.extname(file.originalname));
    }
});

const imageFilter = function (req, file, cb) {
    // Accept images only
    if (!file.originalname.match(/\.(jpg|JPG|jpeg|JPEG|png|PNG|gif|GIF)$/)) {
        req.fileValidationError = 'Only image files are allowed!';
        return cb(new Error('Only image files are allowed!'), false);
    }
    cb(null, true);
};

const upload = multer({
    storage: storage,
    fileFilter: imageFilter
})
// ============ middleware up load file ==============

const initWebRoute = (app) => {
    // ========= get form =========== 
    app.get('/', getFormLogin)
    app.get('/get-form-register', getFormRegister)
    app.get('/admins', getHomePage)
    app.get('/products', getProductPage)
    app.get('/ultrabook', getUltrabookPage)
    app.get('/gaming', getGamingPage)
    app.get('/macbook', getMacBookPage)
    app.get('/create-product', getCreateProductPage)
    app.get('/details/product/:id', getDetailsProductPage)
    app.get('/update-product/:id', getUpdateProductPage)
    app.get('/users', getUserPage)
    app.get('/details/admin/:id', getDetailsAdminPage)
    app.get('/update-admin/:id', getEditAdminPage)
    app.get('/details/user/:id', getDetailsUserPage)
    app.get('/update-user/:id', getEditUserPage)
    app.get('/order', getOrderPage)
    app.get('/details/order/:id', getOrderDetails)
    // ========= get form =========== 

    // ========= action =============
    app.post('/handle-login', handleLogin)
    app.post('/handle-remove-product', handleDeleteProduct)
    app.post('/handle-register', upload.single('pic_admin'), handleRegister)
    app.post('/handle-create-product', upload.single('pic_product'), handleCreateProduct)
    app.post('/handle-update-product', upload.single('pic_product_update'), handleUpdateProduct)
    app.post('/handle-update-admin', upload.single('pic_admin_update'), handleUpdateAdmin)
    app.post('/handle-update-user', upload.single('pic_user_update'), handleUpdateUser)
    app.post('/handle-delete-admin', handleDeleteAdmin)
    app.post('/handle-delete-user', handleDeleteUser)
    app.post('/confirm-order', confirmOrder)
    // ========= action =============

    return app.use('/', route)
}

export default initWebRoute