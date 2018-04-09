#********************************** IMPORTS  **********************************
from flask_sqlalchemy import SQLAlchemy
from flask.ext.login import UserMixin
from datetime import datetime

db = SQLAlchemy()

#********************************** ASSOCIATION TABLES  **********************************

#********************************** MODELS  **********************************

class User(db.Model, UserMixin):
    __tablename__ = 'user'

    id = db.Column(db.Integer, unique=True, primary_key=True)
    username = db.Column(db.String(20), unique=True)
    password = db.Column(db.String(120))

    def __init__(self, username, password):
        self.username = username
        self.password = password

    def __repr__(self):
        return '<User %r,  p: %r>' % (self.username, self.password)



class ChatRoom(db.Model):
    __tablename__ = 'chat_room'

    id = db.Column(db.Integer, unique = True, primary_key = True)
    title = db.Column(db.String(100))
    description = db.Column(db.String(250))

    creator_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable = False)
    participants = db.relationship('User', backref=db.backref('ChatRoom', lazy = True))

    def __init__(self, title, description, creator_id):
        self.title = title
        self.description = description
        self.creator_id = creator_id

    def __repr__(self):
        return '<ChatRoom %r>' % (self.title)


class Message(db.Model):
    __tablename__ = 'message'

    id = db.Column(db.Integer, primary_key = True, unique = True)
    content = db.Column(db.Text)
    time = db.Column(db.DateTime)

    sender_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable = False)
    room_id = db.Column(db.Integer, db.ForeignKey('chat_room.id'), nullable = False)

    def __init__(self, content, time, sender, room):
        self.content = content
        self.time = time
        self.sender_id = sender
        self.room_id = room

    def __repr__(self):
        return '<Message %r, %r>' % (self.content, self.sender_id)
