import { pool } from '../configs/index'
import multer from 'multer'
import { v4 as uudiv4 } from 'uuid'
import '../configs/firebase'
import { storage } from '../configs/firebase'

const getFormLogin = (req, res) => {
    return res.render('login.ejs')
}

const getFormRegister = (req, res) => {
    return res.render('register.ejs')
}

const getHomePage = async (req, res) => {
    const [rows, fields] = await pool.execute(
        `select * from admins`
    )
    return res.render('admins.ejs', { data: rows })
}

const getProductPage = async (req, res) => {
    const [rows, fields] = await pool.execute(
        `select * from products`
    )

    return res.render('products.ejs', { data: rows })
}

const getDetailsProductPage = async (req, res) => {
    const id = req.params.id

    const [product] = await pool.execute(
        `select * from products where id = ?`, [id]
    )

    return res.render('product_details.ejs', { data: product[0] })
}

const getUpdateProductPage = async (req, res) => {
    const id = req.params.id

    const [product] = await pool.execute(
        `select * from products where id = ?`, [id]
    )

    return res.render('product_update.ejs', { data: product[0] })
}

const getUltrabookPage = async (req, res) => {
    const [rows, fields] = await pool.execute(
        `select * from products where category = 'ultrabook'`
    )

    return res.render('products_ultrabook.ejs', { data: rows })
}

const getGamingPage = async (req, res) => {
    const [rows, fields] = await pool.execute(
        `select * from products where category = 'gaming'`
    )

    return res.render('products_gaming.ejs', { data: rows })
}

const getMacBookPage = async (req, res) => {
    const [rows, fields] = await pool.execute(
        `select * from products where category = 'macbook'`
    )

    return res.render('products_macbook.ejs', { data: rows })
}

const getCreateProductPage = (req, res) => {
    return res.render('product_create.ejs')
}

const getUserPage = async (req, res) => {
    const [rows, fields] = await pool.execute(
        `select * from users`
    )
    return res.render('users.ejs', { data: rows })
}

const getDetailsAdminPage = async (req, res) => {
    const id = req.params.id

    const [admin] = await pool.execute(
        `select * from admins where id = ?`, [id]
    )

    return res.render('admins_details.ejs', { data: admin[0] })
}

const getEditAdminPage = async (req, res) => {
    const id = req.params.id

    const [admin] = await pool.execute(
        `select * from admins where id = ?`, [id]
    )

    return res.render('admin_edit.ejs', { data: admin[0] })
}

const getDetailsUserPage = async (req, res) => {
    const id = req.params.id

    const [user] = await pool.execute(
        `select * from users where id = ?`, [id]
    )

    return res.render('user_details.ejs', { data: user[0] })
}

const getEditUserPage = async (req, res) => {
    const id = req.params.id

    const [user] = await pool.execute(
        `select * from users where id = ?`, [id]
    )

    return res.render('user_edit.ejs', { data: user[0] })
}

const getOrderPage = async (req, res) => {

    const [rows, fields] = await pool.execute(
        `select * from orders`
    )

    return res.render('order.ejs', { data: rows })
}

const getOrderDetails = async (req, res) => {
    const id = req.params.id

    const [infoUser] = await pool.execute(
        `select * from orders 
        inner join users on orders.idUser = users.id 
        where 
            orders.idUser = users.id 
            and orders.id = ?`, [id]
    )

    const [products] = await pool.execute(
        `select * from carts 
            inner join orders on carts.idOrder = orders.id
            inner join products on carts.idProduct = products.id
            where carts.idOrder = orders.id
            and orders.id = ?`, [id]
    )

    var priceSum = 0
    for (let i = 0; i < products.length; i++) {
        priceSum += (products[i].price - (products[i].price * products[i].discount / 100)) * products[i].productQuantity
    }

    // setTimeout(() => {
    //     // console.log(infoUser[0]);
    //     console.log(products);


    // }, 2000);

    return res.render('orderDetails.ejs',
        {
            idOrder: id,
            infoUser: infoUser[0],
            products: products,
            priceSum: priceSum
        })
}

const handleRegister = async (req, res) => {
    /**
     * Mô tả: lấy file từ trang html 
     *      => lưu file vào folder images 
     *      => đẩy file lên firebase 
     *      => lấy link http của file trên file bây 
     *      => insert vào db
     */
    const id = uudiv4()
    const { email, password, phoneNumber, isBoss } = req.body
    var imageName
    // =============== handle upload file ===================
    const upload = multer().single('pic_user')
    upload(req, res, async function (err) {
        if (req.fileValidationError) {
            return res.send(req.fileValidationError);
        }
        else if (!req.file) {
            return res.send('Please select an image to upload');
        }
        else if (err instanceof multer.MulterError) {
            return res.send(`Error 1: ${err}`);
        }
        else if (err) {
            return res.send(`Error 2: ${err}`);
        }
        imageName = await req.file.filename
    });
    // =============== handle upload file ===================

    setTimeout(() => {
        // ================= handle upload to firebase ========================
        const filePath
            = `C:\\Users\\FPT\\Desktop\\MyProject\\Project_Android\\Laptop_App_Kotlin\\Server\\src\\public\\images\\${imageName}`
        storage.upload(filePath, {
            destination: `image_admin/${imageName}` // => đây là đường dẫn tới folder ảnh trên firebase
        }).then(() => {
            // ================ handle get to http image ======================
            const file = storage.file(`image_admin/${imageName}`)
            file.getSignedUrl({
                action: 'read',
                expires: '03-17-2150'
            })
                .then(async (url) => {
                    console.log(`Check url: ${url[0]}`);
                    await pool.execute(
                        `insert into admins(id, email, password, phoneNumber, isBoss, image)
                                values(?, ?, ?, ?, ?, ?)`,
                        [id, email, password, phoneNumber, isBoss, String(url[0])]
                    )

                    return res.redirect('/')
                })
                .catch((err) => {
                    console.log(err);
                })
            // ================ handle get to http image ======================
        }).catch((err) => {
            console.log(err);
        })
        // ================= handle upload to firebase ========================
    }, 2000);
}

const handleLogin = async (req, res) => {
    const { email, password } = req.body

    // handle login
    const [admin] = await pool.execute(
        `select * from admins where email = ? and password = ?`,
        [email, password]
    )

    let result = (admin[0] == undefined || !admin[0] || admin[0] == null)
        ? res.render('login.ejs')
        : (
            res.redirect('/admins')
        )

    return result
}

const handleCreateProduct = async (req, res) => {
    const id = uudiv4()
    const { productName, price, discount, description, quantity, linkVideo, category } = req.body
    const purchases = 0
    var imageName
    // =============== handle upload file ===================
    const upload = multer().single('pic_user')
    upload(req, res, async function (err) {

        if (req.fileValidationError) {
            return res.send(req.fileValidationError);
        }
        else if (!req.file) {
            return res.send('Please select an image to upload');
        }
        else if (err instanceof multer.MulterError) {
            return res.send(`Error 1: ${err}`);
        }
        else if (err) {
            return res.send(`Error 2: ${err}`);
        }
        imageName = await req.file.filename
    });
    // =============== handle upload file ===================

    setTimeout(() => {
        // ================= handle upload to firebase ========================
        const filePath
            = `C:\\Users\\FPT\\Desktop\\MyProject\\Project_Android\\Laptop_App_Kotlin\\Server\\src\\public\\images\\${imageName}`
        storage.upload(filePath, {
            destination: `image_product/${imageName}`
        }).then(() => {
            // ================ handle get to http image ======================
            const file = storage.file(`image_product/${imageName}`)
            file.getSignedUrl({
                action: 'read',
                expires: '03-17-2150'
            })
                .then(async (url) => {
                    console.log(`Check url: ${url[0]}`);
                    await pool.execute(
                        `insert into products (id, name, price, discount, description, image, quantity, category, purchases, linkVideoReview)
                        values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`,
                        [id, productName, price, discount, description, String(url[0]), quantity, category, purchases, linkVideo]
                    )
                    return res.redirect('/products')
                })
                .catch((err) => {
                    console.log(err);
                })
            // ================ handle get to http image ======================
        }).catch((err) => {
            console.log(err);
        })
        // ================= handle upload to firebase ========================
    }, 2000);
}

const handleUpdateProduct = async (req, res) => {
    /*
    *  Giải thích code: 
    *  nếu người dùng có update ảnh mới thì sẽ thực hiện logic sau: 
    *       -> Thêm ảnh vào firebase -> Lấy url ảnh lưu vào database -> update
    *  nếu người dùng không update ảnh mới sẽ thực hiện logic sau: 
    *       -> tìm product bằng id -> lấy giá trị url ảnh cũ gán vào biến imageName -> update
    */

    const { productName, price, description, quantity, category, id, linkVideo, discount } = req.body
    var imageName

    const [product] = await pool.execute(
        `select * from products where id = ?`, [id]
    )

    const purchases = product[0].purchases

    const upload = multer().single('pic_product_update')
    upload(req, res, async function (err) {

        if (req.fileValidationError) {
            return res.send(req.fileValidationError);
        }
        // => đây là trường hợp người dùng không up date image
        else if (!req.file) {
            imageName = product[0].image
            // handle update
            await pool.execute(
                `update products set name = ?, price = ?, description = ?, image = ?, quantity = ?, category = ?, purchases = ?, discount = ?, linkVideoReview = ? where id = ?`,
                [productName, price, description, imageName, quantity, category, purchases, discount, linkVideo, id]
            )

            return res.redirect('/products')
        }
        else if (err instanceof multer.MulterError) {
            return res.send(`Error 1: ${err}`);
        }
        else if (err) {
            return res.send(`Error 2: ${err}`);
        }

        imageName = req.file.filename
    });

    // => đây là trường hợp người dùng udpate image
    setTimeout(async () => {
        // ================= handle upload to firebase ========================
        const filePath
            = `C:\\Users\\FPT\\Desktop\\MyProject\\Project_Android\\Laptop_App_Kotlin\\Server\\src\\public\\images\\${imageName}`
        storage.upload(filePath, {
            destination: `image_product_update/${imageName}`
        }).then(() => {
            // ================ handle get to http image ======================
            const file = storage.file(`image_product_update/${imageName}`)
            file.getSignedUrl({
                action: 'read',
                expires: '03-17-2150'
            })
                .then(async (url) => {
                    console.log(`Check url: ${url[0]}`);
                    await pool.execute(
                        `update products set name = ?, price = ?, description = ?, image = ?, quantity = ?, category = ?, purchases = ?, discount = ?, linkVideoReview = ? where id = ?`,
                        [productName, price, description, String(url[0]), quantity, category, purchases, discount, linkVideo, id]
                    )

                    return res.redirect('/products')
                })
                .catch((err) => {
                    console.log(err);
                })
            // ================ handle get to http image ======================
        }).catch((err) => {
            console.log(err);
        })
    }, 2000);


}

const handleDeleteProduct = async (req, res) => {
    const { id } = req.body

    try {
        console.log(`>>>>> Check id remove: ${id}`);
        if (!id) {
            return res.send('Id is null')
        } else {
            await pool.execute(
                `delete from products where id = ?`, [id]
            )

            return res.redirect('/products')
        }
    } catch (err) {
        return res.send('You can not remove this object!!!')
    }
}

const handleUpdateAdmin = async (req, res) => {
    /*
    *  Giải thích code: 
    *  nếu người dùng có update ảnh mới thì sẽ thực hiện logic sau: 
    *       -> Thêm ảnh vào firebase -> Lấy url ảnh lưu vào database -> update
    *  nếu người dùng không update ảnh mới sẽ thực hiện logic sau: 
    *       -> tìm user bằng id -> lấy giá trị url ảnh cũ gán vào biến imageName -> update
    */
    const { email, password, phoneNumber, id, isBoss } = req.body
    var imageName

    const [admin] = await pool.execute(
        `select * from admins where id = ?`, [id]
    )

    const upload = multer().single('pic_admin_update')
    upload(req, res, async function (err) {

        if (req.fileValidationError) {
            return res.send(req.fileValidationError);
        }
        else if (!req.file) {
            imageName = admin[0].image
            await pool.execute(
                `update admins set email = ?, password = ?, phoneNumber = ?, isBoss = ?, image = ? where id = ?`,
                [email, password, phoneNumber, isBoss, imageName, id]
            )

            return res.redirect('/admins')
        }
        else if (err instanceof multer.MulterError) {
            return res.send(`Error 1: ${err}`);
        }
        else if (err) {
            return res.send(`Error 2: ${err}`);
        }
        imageName = req.file.filename
    });
    // =============== handle upload file ===================

    setTimeout(() => {
        // ================= handle upload to firebase ========================
        const filePath
            = `C:\\Users\\FPT\\Desktop\\MyProject\\Project_Android\\Laptop_App_Kotlin\\Server\\src\\public\\images\\${imageName}`
        storage.upload(filePath, {
            destination: `image_admin_update/${imageName}`
        }).then(() => {
            // ================ handle get to http image ======================
            const file = storage.file(`image_admin_update/${imageName}`)
            file.getSignedUrl({
                action: 'read',
                expires: '03-17-2150'
            })
                .then(async (url) => {
                    console.log(`Check url: ${url[0]}`);
                    await pool.execute(
                        `update admins set email = ?, password = ?, phoneNumber = ?, isBoss = ?, image = ? where id = ?`,
                        [email, password, phoneNumber, isBoss, String(url[0]), id]
                    )

                    return res.redirect('/admins')
                })
                .catch((err) => {
                    console.log(err);
                })
            // ================ handle get to http image ======================
        }).catch((err) => {
            console.log(err);
        })
        // ================= handle upload to firebase ========================
    }, 2000);
}

const handleUpdateUser = async (req, res) => {
    /*
    *  Giải thích code: 
    *  nếu người dùng có update ảnh mới thì sẽ thực hiện logic sau: 
    *       -> Thêm ảnh vào firebase -> Lấy url ảnh lưu vào database -> update
    *  nếu người dùng không update ảnh mới sẽ thực hiện logic sau: 
    *       -> tìm user bằng id -> lấy giá trị url ảnh cũ gán vào biến imageName -> update
    */
    const { email, userName, password, phoneNumber, address, id } = req.body
    var imageName

    const [user] = await pool.execute(
        `select * from users where id = ?`, [id]
    )

    const upload = multer().single('pic_user_update')
    upload(req, res, async function (err) {

        if (req.fileValidationError) {
            return res.send(req.fileValidationError);
        }
        else if (!req.file) {
            imageName = user[0].image
            await pool.execute(
                `update users set email = ?, userName = ?, password = ?, phoneNumber = ?, address = ?, image = ? where id = ?`,
                [email, userName, password, phoneNumber, address, imageName, id]
            )

            return res.redirect('/users')
        }
        else if (err instanceof multer.MulterError) {
            return res.send(`Error 1: ${err}`);
        }
        else if (err) {
            return res.send(`Error 2: ${err}`);
        }
        imageName = req.file.filename
    });
    // =============== handle upload file ===================

    setTimeout(() => {
        // ================= handle upload to firebase ========================
        const filePath
            = `C:\\Users\\FPT\\Desktop\\MyProject\\Project_Android\\Laptop_App_Kotlin\\Server\\src\\public\\images\\${imageName}`
        storage.upload(filePath, {
            destination: `image_user_update/${imageName}`
        }).then(() => {
            // ================ handle get to http image ======================
            const file = storage.file(`image_user_update/${imageName}`)
            file.getSignedUrl({
                action: 'read',
                expires: '03-17-2150'
            })
                .then(async (url) => {
                    console.log(`Check url: ${url[0]}`);
                    await pool.execute(
                        `update users set email = ?, userName = ?, password = ?, phoneNumber = ?, address = ?, image = ? where id = ?`,
                        [email, userName, password, phoneNumber, address, String(url[0]), id]
                    )

                    return res.redirect('/users')
                })
                .catch((err) => {
                    console.log(err);
                })
            // ================ handle get to http image ======================
        }).catch((err) => {
            console.log(err);
        })
        // ================= handle upload to firebase ========================
    }, 2000);
}

const handleDeleteAdmin = async (req, res) => {
    const { id } = req.body

    try {
        if (!id || id == null || id == undefined) {
            return res.send('id is null')
        } else {
            await pool.execute(
                `delete from admins where id = ?`, [id]
            )

            return res.redirect('/admins')
        }
    } catch (err) {
        return res.send('You can not delete this object!')
    }
}

const handleDeleteUser = async (req, res) => {
    const { id } = req.body

    try {
        if (!id || id == null || id == undefined) {
            return res.send('id is null')
        } else {
            await pool.execute(
                `delete from users where id = ?`, [id]
            )

            return res.redirect('/users')
        }
    } catch (err) {
        return res.send('You can not delete this object!')
    }
}

const confirmOrder = async (req, res) => {
    const { id, shippingStatus } = req.body

    if (shippingStatus == -1) {
        await pool.execute(
            `update orders set shippingStatus = ? where id = ?`, [0, id]
        )
    }

    return res.redirect('/order')
}

export {
    getFormLogin,
    getFormRegister,
    handleRegister,
    handleLogin,
    getHomePage,
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
}