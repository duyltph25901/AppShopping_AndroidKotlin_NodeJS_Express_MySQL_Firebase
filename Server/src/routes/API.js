import express from 'express'
import bodyParser from 'body-parser'
import {
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
} from '../controllers/API'

let route = express.Router()

const initAPIRoute = (app) => {
    app.post('/handleRegisterUser', handleRegisterUser)
    app.post('/handleLoginUser', bodyParser.json(), handleLoginUser)
    app.post('/searchProductByName', bodyParser.json(), searchProductByName)
    app.post('/addProductToCart', bodyParser.json(), addProductToCart)
    app.post('/updateUser', bodyParser.json(), updateUser)
    app.post('/addOrder', addOrder)
    app.post('/addCart', addCart)

    app.get('/getCarts', getCarts)
    app.get('/getProducts', getProducts)
    app.get('/getMacbook', getMacBook)
    app.get('/getGaming', getGaming)
    app.get('/getUltrabook', getUltrabook)
    app.get('/getUsers', getUsers)
    app.get('/getAllProductsName', getAllProductsName)
    app.get('/getOrders', getOrders)

    app.put('/handleUpdateCart/:id', bodyParser.json(), handleUpdateCart)

    app.delete('/handleDeleteCart/:id', handleDeleteCart)

    return app.use('/', route)
}

export default initAPIRoute