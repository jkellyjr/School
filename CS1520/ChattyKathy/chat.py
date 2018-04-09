#********************************** IMPORTS  **********************************
from flask import Flask, render_template, request, redirect, url_for, flash, g
from flask_login import login_user, logout_user, login_required, current_user
from werkzeug.security import generate_password_hash, check_password_hash
from flask_login import LoginManager
from models import db, User, ChatRoom
from forms import SignUpForm, LoginForm


#********************************** CONFIIGURATION  **********************************
app = Flask(__name__)
app.secret_key = 'super-duper-secret-key'
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///catering.db'
db.init_app(app)

login_manager = LoginManager()
login_manager.init_app(app)
login_manager.login_view = 'login'


#********************************** INITIALIZATION  **********************************
@app.cli.command('initdb')
def initdb_command():
    """Reinitializes the database"""
    db.drop_all()
    db.create_all()

    print('Created Database')

#********************************** HELPERS  **********************************
@app.before_request
def before_request():
    g.user = current_user
    if g.user.is_authenticated:
        db.session.add(g.user)
        db.session.commit()

@login_manager.user_loader
def load_user(user_id):
    return User.query.get(user_id)


#********************************** VIEWS  **********************************

@app.route('/login', methods = ['GET', 'POST'])
def login():
    if g.user is not None and g.user.is_authenticated:
        flash('You are already signed in')
        return redirect(url_for('home'))

    form = LoginForm()

    if request.method == 'POST' and form.validate_on_submit():
        user = User.query.filter_by(username = request.form['username']).first()
        if user == None:
            flash('Invalid username provided')
            return redirect(url_for('login'))
        elif not check_password_hash(user.password, request.form['password']):
            flash('Invalid password provided')
            return redirect(url_for('login'))
        else:
            login_user(user)
            return redirect(url_for('home'))
    else:
        return render_template('login.html', form = form)


@app.route('/signup', methods = ['GET', 'POST'])
def signup():
    form = SignUpForm()
    if request.method == 'POST' and form.validate_on_submit():
        if request.form['password'] != request.form['confirm_password']:
            flash("Passwords do not match")
            return render_template('signup.html', form)
        else:
            user = User(request.form['username'],  generate_password_hash(request.form['password']))
            if user != None:
                login_user(user)
                return redirect(url_for('home'))
    else:
        return render_template('signup.html', form = form)


@app.route('/logout')
def logout():
    logout_user()
    return redirect(url_for('home'))


@app.route('/', methods = ['GET', 'POST'])
@login_required
def home():
    rooms = ChatRoom.query.all()
    return render_template('index.html', user = g.user, rooms = rooms)


@app.route('/chat_room/<id>', methods = ['GET', 'POST'])
@login_required
def chat_room(id = None):
    if request.method == 'POST':
        print('post method dawg')
    elif id != None:
        #TODO: query messages
        room = ChatRoom.query.filter_by(id = id).first()
        return render_template('chat_room.html', room = room)
    else:
        flash('Invalid chat room')
        return redirect(url_for('home'))


@app.route('/message', methods = ['GET', 'POST'])
@login_required
def message(roomId = None):
    print('trying to create new message')
    if request.args.get('roomId'):
        print('\n\n found an id: ' + request.args.get('roomId'))


@app.route('/create_chat', methods = ['POST'])
def create_chat():
    title = request.form['title']
    description = request.form['description']

    db.session.add(ChatRoom(title, description, g.user.id))
    db.session.commit()

    return redirect(url_for('home'))


@app.route('/delete_chat/<id>', methods = ['POST'])
def delete_chat(id = None):
    print("tring to delete this shit")
    if id != None:

        room = ChatRoom.query.filter_by(id = id).first()
        if room == None:
            flash('Error: unable to delete the room')
            abort(500)
        db.session.delete(room)
        db.session.commit()
        return redirect(url_for('home'))
    else:
        flash("Invalid room id")
        return redirect(url_for('home'))
