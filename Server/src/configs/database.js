import sql from 'mysql2/promise'

const pool = sql.createPool({
    host: 'localhost',
    user: 'root',
    database: 'laptop_app_kotline'
})

export default pool