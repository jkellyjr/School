from flask.ext.login import UserMixin
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

#================== Association Tables ===================
signups = db.Table('signups',
    db.Column('staff_id', db.Integer, db.ForeignKey('user.id')),
    db.Column('event_id', db.Integer, db.ForeignKey('event.id'))
)

#================== Models ===================
class User(db.Model, UserMixin):
    __tablename__ = 'user'

    id = db.Column(db.Integer, unique=True, primary_key=True)
    username = db.Column(db.String(20), unique=True)
    password = db.Column(db.String(120))
    role = db.Column(db.String(20))
    event_signups = db.relationship('Event', secondary = signups, backref = db.backref('event_staff', lazy = 'dynamic'))

    def __init__(self, username, password, role):
        self.username = username
        self.password = password
        self.role = role

    def __repr__(self):
        return '<User %r,  p: %r>' % (self.username, self.password)



class Event(db.Model):
    __tablename__ = 'event'

    id = db.Column(db.Integer, unique=True, primary_key=True)
    title = db.Column(db.String(120))
    date = db.Column(db.Date)

    customer_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)

    def __init__(self, title, customer_id, date):
        self.title = title
        self.customer_id = customer_id
        self.date = date

    def __repr(self):
        return '<Event %r>' % self.title
