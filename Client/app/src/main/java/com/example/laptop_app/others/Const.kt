package com.example.laptop_app.others

object Const {
    // key
    const val MY_SHARED_PREFERENCES: String = "MY_SHARED_PREFERENCES"
    const val PREF_FIRST_INSTALL: String = "PREF_FIRST_INSTALL"
    const val intentProduct: String = "KEY_INTENT_PRODUCT"

    const val all: Int = 0
    const val idMacbook: Int = 1
    const val idGamingLaptop: Int = 2
    const val idUltrabook: Int = 3

    const val bottomHome: Int = 0
    const val bottomCart: Int = 1
    const val bottomOrder: Int = 2
    const val bottomAccount: Int = 3

    // message
    const val nullMessage: String = "Vui lòng điền đầy đủ thông tin!"
    const val errorEmailMessage: String = "Email không đúng định dạng!"
    const val errorUserNameMessage: String = "Tên phải chứa ít nhất 3 kí tự!"
    const val errorPasswordMessage: String = "Mật khẩu phải chứa ít nhất 6 kí tự!"
    const val errorConfirmPasswordMessage: String = "Mật khẩu không trùng khớp!"
    const val errorNotMatchesOldPass: String = "Mật khẩu không đúng!"
    const val errorPhoneNumberMessage: String = "Không tìm thấy số điện thoại!"
    const val errorNullInfo: String = "Vui lòng cập nhật đầy đủ thông tin!"

    // link
    const val linkAvatarDefault: String = "https://firebasestorage.googleapis.com/v0/b/app-lap-top-kotlin.appspot.com/o/image_user%2Fimg_avatar_default.png?alt=media&token=495c2dc5-6af7-49fb-a4a0-087999f883a0"
    private const val host:String = "192.168.1.11"
    private const val port: String = "3000"
    const val url:String = "http://${host}:${port}/"

    // api
    const val handleRegister: String = "handleRegisterUser"
    const val handleLogin: String = "handleLoginUser"
    const val getAllUser: String = "getUsers"
    const val getAllProduct: String = "getProducts"
    const val getMacbook: String = "getMacbook"
    const val getUltrabook: String = "getUltrabook"
    const val getGaming: String = "getGaming"
    const val getCarts: String = "getCarts?"
    const val updateCart: String = "handleUpdateCart/{id}"
    const val deleteCart: String = "handleDeleteCart/{id}"
    const val getAllProductsName: String = "getAllProductsName"
    const val searchProductByName: String = "searchProductByName"
    const val addProductToCart: String = "addProductToCart"
    const val updateUser: String = "updateUser"
    const val addOrder: String = "addOrder"
    const val addCart: String = "addCart"
    const val getOrder: String = "getOrders?"

    // date
    const val dateFormat: String = "dd-MM-yyyy HH:mm:ss"

    // name
    const val dbLocalUser: String = "User.db"
    const val dbSystem: String = "System.db"

    // result
    const val success: Int = 1
    const val fail: Int = -1

    // status
    const val waitingConfirm: Int = -1
    const val shipping: Int = 0
    const val successfulDelivery: Int = 1
    const val COD: Int = -1
    const val BANK: Int = 1
}