from peewee import *

db = SqliteDatabase('orders.db')


class BaseModel(Model):
	class Meta:
		database = db


class Courier(BaseModel):
	courier_id = IntegerField(primary_key=True, null=False)
	surname = CharField()
	name = CharField()
	patronymic = CharField()
	passport_id = CharField()
	birth_date = CharField()
	hiring_date = CharField()
	start_work_day = CharField()
	end_work_day = CharField()
	city = CharField()
	street = CharField()
	house = CharField()
	flat = CharField()
	phone = CharField()

	class Meta:
		db_table = 'courier'


class Transport(BaseModel):
	car_lic_plate = CharField(primary_key=True, null=False)
	brand = CharField()
	registration_date = CharField()
	color = CharField()

	class Meta:
		db_table = 'transport'


class Sender(BaseModel):
	sender_id = IntegerField(primary_key=True, null=False)
	surname = CharField()
	name = CharField()
	patronymic = CharField()
	birth_date = CharField()
	index = CharField()
	city = CharField()
	street = CharField()
	house = CharField()
	flat = CharField()
	phone = CharField()

	class Meta:
		db_table = 'sender'


Sender.create_table()


class Recipient(BaseModel):
	recipient_id = IntegerField(primary_key=True, null=False)
	surname = CharField()
	name = CharField()
	patronymic = CharField()
	birth_date = CharField()
	index = CharField()
	city = CharField()
	street = CharField()
	house = CharField()
	flat = CharField()
	phone = CharField()

	class Meta:
		db_table = 'recipient'


Recipient.create_table()


class Order(BaseModel):
	__tablename__ = 'order'
	order_id = IntegerField(primary_key=True, null=False)
	sender_id = ForeignKeyField(Sender, related_name='sender_id', null=False)
	recipient_id = ForeignKeyField(Sender, related_name='recipient_id', null=False)
	order_date = CharField()
	delivery_date = CharField()
	delivery_price = CharField()
	courier_id = ForeignKeyField(Courier, related_name='courier_id', null=False)
	transport_id = ForeignKeyField(Transport, related_name='transport_id', null=False)

	class Meta:
		db_table = 'order'


Order.create_table()

sender1 = Sender.create(
	sender_id=1,
	surname='Ivanov',
	name='Ivan',
	patronymic='Ivanovich',
	birth_date='1980-01-01',
	index='123456',
	city='Moscow',
	street='Tverskaya',
	house='1',
	flat='1',
	phone='+7(999)999-99-99'
)
sender1.save()

sender2 = Sender.create(
	sender_id=1,
	surname='Sergeev',
	name='Sergey',
	patronymic='Sergeevich',
	birth_date='2000-01-01',
	index='123333',
	city='Moscow',
	street='Myasnitskaya',
	house='1',
	flat='1',
	phone='+7(999)123-99-99'
)
sender2.save()

recipient1 = Recipient.create(
	recipient_id=1,
	surname='Petrov',
	name='Petr',
	patronymic='Petrovich',
	birth_date='1990-01-01',
	index='654321',
	city='Saint Petersburg',
	street='Nevsky',
	house='10',
	flat='20',
	phone='+7(999)888-88-88'
)
recipient1.save()

recipient2 = Recipient.create(
	recipient_id=2,
	surname='Sidorov',
	name='Sidr',
	patronymic='Sidorovich',
	birth_date='1995-01-01',
	index='654322',
	city='Saint Petersburg',
	street='Liteyny',
	house='15',
	flat='25',
	phone='+7(999)777-77-77'
)
recipient2.save()

order1 = Order.create(
	order_id=1,
	sender_id=1,
	recipient_id=1,
	order_date='2024-01-01',
	delivery_date='2024-01-02',
	delivery_price='1000',
	courier_id=1,
	transport_id='A123AA'
)
order1.save()

order2 = Order.create(
	order_id=2,
	sender_id=2,
	recipient_id=2,
	order_date='2024-01-03',
	delivery_date='2024-01-04',
	delivery_price='1500',
	courier_id=1,
	transport_id='A123AA'
)
order2.save()
