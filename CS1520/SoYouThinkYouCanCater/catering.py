#********************************** IMPORTS  **********************************
from flask import Flask, abort, render_template, request, redirect, url_for, flash, g
from models import db, User, Event
from werkzeug.security import generate_password_hash, check_password_hash
from forms import LoginForm, SignUpForm, EventForm
from flask_login import login_user, logout_user, login_required, current_user
import datetime
from werkzeug.security import generate_password_hash
from flask.ext.login import LoginManager


app = Flask(__name__)
app.secret_key = 'super-duper-secret-key'

app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///catering.db'

login_manager = LoginManager()
login_manager.init_app(app)
login_manager.login_view = 'login'

db.init_app(app)


#********************************** INIT  **********************************

@app.cli.command('initdb')
def initdb_command():
    """Reinitializes the database"""
    db.drop_all()
    db.create_all()

	# Create Owner
    db.session.add(User('owner', generate_password_hash('pass'), 'owner'))
    db.session.commit()

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

# Add a user to the database
def add_user(username, password, confirm_password, user_role, return_fun):
    user = User.query.filter_by(username = username).first()

    if user != None:
        flash('Username already exists')
        return redirect(url_for(return_fun))
    elif password != confirm_password:
        flash('The passwords do not match')
        return redirect(url_for(return_fun))
    else:
        user = User(username, generate_password_hash(password), user_role)
        db.session.add(user)
        db.session.commit()
        return user


#********************************** VIEWS  **********************************
@app.route('/')
@login_required
def home():
    if g.user.role == 'owner':
        form = SignUpForm()
        events = Event.query.filter(Event.date >= datetime.datetime.today())
        eventList = []
        for eve in events:
            workers = []
            for staff in eve.event_staff:
                workers.append(staff)
            eventDict = { "id": eve.id, "title": eve.title, "date": eve.date, "workers": workers, "num_workers": len(workers) }
            eventList.append(eventDict)
        return render_template('index_owner.html', user = g.user, form = form, events = eventList)
    elif g.user.role == 'staff':
        form = EventForm()
        events = Event.query.filter(Event.date >= datetime.datetime.today())
        prospectList = []
        workList = []
        for eve in events:
            tmp = []
            for staff in eve.event_staff:
                if staff.id != g.user.id:
                    tmp.append(staff)
                    if len(tmp) > 3:
                        prospectList.append(eve)
                else:
                    workList.append(eve)
        return render_template('index_staff.html', user = g.user, workList = workList, possibleEvents = prospectList)
    else:
        form = EventForm()
        events = Event.query.filter(Event.customer_id == g.user.id, Event.date >= datetime.datetime.today())
        return render_template('index_customer.html', user = g.user, form = form, events = events)


@app.route('/login', methods = ['POST', 'GET'])
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
@app.route('/signup/<userType>', methods = ['POST'])
def signup(userType = None):
    form = SignUpForm()
    if (userType != None):
        user_type = userType

    if request.method == 'POST' and form.validate_on_submit():
        user = add_user(request.form['username'], request.form['password'], request.form['confirm_password'], user_type, 'signup')
        if user != None:
            if user_type == 'customer':
                login_user(user)
        return redirect(url_for('home'))
    else:
        return render_template('signup.html', form = form)


@app.route('/logout')
def logout():
    logout_user()
    return redirect(url_for('home'))

@app.route('/event', methods = ['POST'])
@app.route('/event/<eventId>', methods=['GET', 'POST'])
@login_required
def event(eventId = None):
    if request.method == 'POST':
        dateIn = datetime.datetime.strptime(request.form["date"], "%Y-%m-%d").date()
        event = Event(request.form['title'], g.user.id, dateIn)
        db.session.add(event)
        db.session.commit()
        return redirect(url_for('home'))
    elif eventId != None:
        event_id = eventId
        event = Event.query.filter_by(id = event_id).first()
        customer = User.query.filter(User.id == event.customer_id).first()
        workers = []
        for user in event.event_staff:
            workers.append(user)
        return render_template('event.html', event = event, customer = customer, staff = workers)


@app.route('/event_signup/<eventId>', methods = ['POST'])
@login_required
def event_signup(eventId = None):
    if g.user.role != 'staff':
        flash("Only staff may register to work for events.")
        return redirect(url_for('home'))
    elif (eventId != None):
        event = Event.query.filter(Event.id == eventId).first()
        event.event_staff.append(g.user)
        db.session.commit()
        flash("Okay " + g.user.username + ", you are now sceduled to work " + event.title)
    return redirect(url_for('home'))


@app.route('/delete_event/<eventId>', methods = ['GET', 'POST'])
@login_required
def delete_event(eventId = None):
    if g.user.role != 'customer':
        flash('Only customers can delete events')
        return redirect(url_for('home'))
    elif eventId != None:
        event = Event.query.filter_by(id = eventId).first()
        db.session.delete(event)
        db.session.commit()
        flash('Okay, ' + event.title + ' has been deleted.')
        return redirect(url_for('home'))







if __name__ == '__main__':
    app.run()
