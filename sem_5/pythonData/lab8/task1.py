import sqlite3

conn = sqlite3.connect('orders.db')

cur = conn.cursor()

cur.execute("""CREATE TABLE IF NOT EXISTS courier
(
    courier_id     INT PRIMARY KEY,
    surname        TEXT,
    name           TEXT,
    patronymic     TEXT,
    passport_id    TEXT,
    birth_date     TEXT,
    hiring_date    TEXT,
    start_work_day TEXT,
    end_work_day   TEXT,
    city           TEXT,
    street         TEXT,
    house          TEXT,
    flat           TEXT,
    phone          TEXT
);
""")

cur.execute("""CREATE TABLE IF NOT EXISTS transport
(
    car_lic_plate     TEXT PRIMARY KEY,
    brand             TEXT,
    registration_date TEXT,
    color             TEXT
);
""")
conn.commit()

courier = (
	1, 'Vasya', 'Pupkin', 'Pupkinovich', '123456789', '2000-01-01', '2024-01-01', '08:00', '17:00', 'Moscow',
	'Lenin Str.', '10',
	'101', '+7(999)999-99-99')
cur.execute("INSERT INTO courier VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);", courier)
conn.commit()

car = ('A123AA', 'Toyota', '2020-01-01', 'Black')
cur.execute("INSERT INTO transport VALUES (?,?,?,?);", car)
conn.commit()

car_update = ('BMW', 'A123AA')
cur.execute("UPDATE transport SET BRAND = ? WHERE CAR_LIC_PLATE = ?;", car_update)
conn.commit()
