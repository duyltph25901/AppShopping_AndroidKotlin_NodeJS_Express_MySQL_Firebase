import { pool } from '../configs/index'

const handleRegisterUser = async (req, res) => {
    const { id, email, userName, password, phoneNumber, address, image } = req.body

    try {
        await pool.execute(
            `insert into users (id, email, userName, password, phoneNumber, address, image)
            values(?, ?, ? ,?, ?, ?, ?)`,
            [id, email, userName, password, phoneNumber, address, image]
        )

        return res.status(200).json({
            message: 'Ok'
        })
    } catch (err) {
        return res.status(200).json({
            message: err
        })
    }
}

const handleLoginUser = async (req, res) => {
    const email = req.body.email
    const password = req.body.password

    console.log(
        `Email: ${email} and Password: ${password}`
    );

    const [user] = await pool.execute(
        `select * from users where email = '${email}' and password = '${password}'`,
    )

    setTimeout(() => {
        console.log(
            `Result: ${JSON.stringify(user[0])}`
        );
        console.log(
            `Type: ${typeof (user[0])}`
        );
    }, 2000);

    return res.status(200).json(
        user[0]
    )
}

const handleUpdateCart = async (req, res) => {
    const id = req.params.id
    const cart = req.body

    console.log(
        `>>>> Check id: ${id}`
    );

    if (cart.idOrder === undefined || cart.idOrder === null) {
        await pool.execute(
            `
                update carts
                set productQuantity = ?
                where id = ?
            `, [cart.productQuantity, id]
        )
    } else {
        await pool.execute(
            `
                update carts
                set idOrder = ?
                where id = ?
            `, [cart.idOrder, id]
        )
    }

    return res.status(200).json(1)
}

const handleDeleteCart = async (req, res) => {
    const id = req.params.id

    await pool.execute(
        `delete from carts where id = ?`, [id]
    )

    return res.status(200).json(1)
}

const getProducts = async (req, res) => {
    const [rows, fields] = await pool.execute('SELECT * FROM products')

    console.log(`All product returned`);

    return res.status(200).json(rows)
}

const getMacBook = async (req, res) => {
    const [rows, fields] = await pool.execute(
        `select * from products where category = 'macbook'`
    )

    return res.status(200).json(rows)
}

const getUltrabook = async (req, res) => {
    const [rows, fields] = await pool.execute(
        `select * from products where category = 'ultrabook'`
    )

    return res.status(200).json(rows)
}

const getGaming = async (req, res) => {
    const [rows, fields] = await pool.execute(
        `select * from products where category = 'gaming'`
    )

    return res.status(200).json(rows)
}

const getCarts = async (req, res) => {
    const { idUser } = req.query

    // get product
    const [products] = await pool.execute(
        `
            select products.* from products
            inner join carts on products.id = carts.idProduct
            where carts.idUser = ?
        `, [idUser]
    )
    // getCart
    const [carts] = await pool.execute(
        `
        select * from carts
        where carts.idUser = ?
        `, [idUser]
    )

    const result = []

    for (let i = 0; i < carts.length; i++) {
        const objectReturn = {
            id: carts[i].id,
            idUser: carts[i].idUser,
            idOrder: carts[i].idOrder,
            product: products[i],
            productQuantity: carts[i].productQuantity,
            priceSum: carts[i].priceSum
        }
        result.push(
            objectReturn
        )
    }

    console.log(
        result
    );

    return res.status(200).json(result)
}

const getUsers = async (req, res) => {
    const [rows, fields] = await pool.execute('SELECT * FROM `users`')

    console.log(
        `Type: ${typeof (rows)}`
    );

    return res.status(200).json(rows)
}

const getAllProductsName = async (req, res) => {
    const [rows, fields] = await pool.execute('select name from products')

    // rows là một mảng object => phải convert về mảng string => code convert mảng object về mảng string
    const valuesWithoutName = rows.map(product => product['name'] === undefined ? product : product['name']);
    const result = valuesWithoutName.filter(value => value !== undefined);

    return res.status(200).json(result)
}

const searchProductByName = async (req, res) => {
    const productName = req.body.name
    console.log(
        `Check productName: ${productName}`
    );
    const querry = `select * from products where name like '%${productName}%'`
    const [products] = await pool.execute(querry)

    console.log(
        `Check found: ${JSON.stringify(products)}`
    );

    return res.status(200).json(products)
}

const addProductToCart = async (req, res) => {
    const { idProductCurrent, idUserCurrent, idCart } = req.body
    console.log(
        `Check idProductCurrent: ${idProductCurrent}\nCheck idUserCurrent: ${idUserCurrent}\nCheck idCart: ${idCart}`
    );

    const result = await pool.execute(
        `select * from carts where idProduct = ? and idUser = ?`, [idProductCurrent, idUserCurrent]
    )

    console.log(`>>>>> Check result found: ${JSON.stringify(result[0])}`);

    if (result[0].length === 0) {
        await pool.execute(
            `insert into carts (id, idUser, idOrder, idProduct, productQuantity)
            values (?, ?, ?, ?, ?)`, [idCart, idUserCurrent, null, idProductCurrent, 1]
        )
    } else {
        const temp = result[0][0]
        console.log(`>>>>> Check object: ${JSON.stringify(temp)}`);
        await pool.execute(
            `update carts 
            set productQuantity = ?
            where id = ?`, [(temp.productQuantity + 1), temp.id]
        )
    }

    return res.status(200).json(1)
}

const updateUser = async (req, res) => {
    const { id, password, phoneNumber, address, email, userName, image } = req.body

    await pool.execute(
        `update users
        set password = ?, phoneNumber = ?, address = ?, email = ?, userName = ?, image = ? where id = ?`
        , [password, phoneNumber, address, email, userName, image, id]
    )

    return res.status(200).json(1)
}

const addOrder = async (req, res) => {
    const { id, idUser, dateTime, shippingStatus, paymentStatus } = req.body

    await pool.execute(
        `insert into orders (id, idUser, dateTime, shippingStatus, paymentStatus)
        values (?, ?, ?, ?, ?)`, [id, idUser, dateTime, shippingStatus, paymentStatus]
    )

    return res.status(200).json(1)
}

const addCart = async (req, res) => {
    const { id, idUser, idOrder, idProduct, productQuantity } = req.body

    await pool.execute(
        `insert into carts (id, idUser, idOrder, idProduct, productQuantity)
        value (?, ?, ?, ?, ?)`, [id, idUser, idOrder, idProduct, productQuantity]
    )

    return res.status(200).json(1)
}

const getOrders = async (req, res) => {
    const { idUser } = await req.query

    const [oders] = await pool.execute(
        `select 
            orders.id as 'id',
            orders.idUser as 'idUser',
            orders.dateTime as 'dateTime',
            orders.shippingStatus as 'shippingStatus',
            orders.paymentStatus as 'paymentStatus',
            products.id as 'idProduct',
            products.name as 'productName',
            products.price as 'productPrice',
            products.description as 'productDescription',
            products.discount as 'productDiscount',
            products.image as 'productImage',
            products.quantity as 'quantity',
            products.purchases as 'purchases',
            products.linkVideoReview as 'linkVideoReview',
            products.category as 'category',
            carts.id as 'idCart',
            carts.productQuantity as 'productQuantity'
        from orders
        inner join carts on carts.idOrder = orders.id
        inner join products on carts.idProduct = products.id
        where orders.idUser = ?`, [idUser]
    ) // => order is array

    const result = []

    for (let i = 0; i < oders.length; ++i) {
        const order = oders[i]
        if (order == null || order == undefined) continue
        const objectReturn = {
            id: order.id,
            idUser: order.idUser,
            dateTime: order.dateTime,
            shippingStatus: order.shippingStatus,
            paymentStatus: order.paymentStatus,
            product: {
                id: order.idProduct,
                name: order.productName,
                price: order.productPrice,
                description: order.productDescription,
                discount: order.productDiscount,
                image: order.productImage,
                quantity: order.quantity,
                purchases: order.purchases,
                linkVideoReview: order.linkVideoReview,
                category: order.category,
            },
            cart: {
                id: order.idCart,
                idUser: order.idUser,
                idOrder: order.id,
                idProduct: order.idProduct,
                productQuantity: order.productQuantity
            }
        }
        result.push(objectReturn)
    }

    return res.status(200).json(result)
}

export {
    handleRegisterUser,
    handleLoginUser,
    handleUpdateCart,
    handleDeleteCart,
    getProducts,
    getUsers,
    getMacBook,
    getUltrabook,
    getGaming,
    getCarts,
    getAllProductsName,
    searchProductByName,
    addProductToCart,
    updateUser,
    addCart,
    addOrder,
    getOrders
}