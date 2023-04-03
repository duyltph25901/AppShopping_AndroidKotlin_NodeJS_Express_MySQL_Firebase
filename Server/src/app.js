import express from 'express'
import { configViewEngine, } from './configs/index'
import { initWebRoute, initAPIRoute } from './routes/index'

const hostName = '127.0.0.1'
const port = 3000
const app = express()

app.use(express.urlencoded({ extends: true }))
app.use(express.json())

// Cấu hình cho con server
configViewEngine(app)
// Khởi tạo web route
initWebRoute(app)
initAPIRoute(app)

app.listen(port, () => {
    console.log(`Server running at http://${hostName}:${port}`)
})
