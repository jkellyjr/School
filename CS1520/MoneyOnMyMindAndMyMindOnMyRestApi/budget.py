from flask import Flask, render_template, url_for, request
from flask_restful import Api, Resource, reqparse

app = Flask(__name__)
api = Api(app)

categories = [{"id": 0, "name": "Uncategorized", "limit": 0}]
purchases = []


''' ------------------------------- Views -------------------------------'''
@app.route('/')
def index():
    return render_template('home.html')


''' ------------------------------- API Endpoints -------------------------------'''
post_category_parser = reqparse.RequestParser()
post_category_parser.add_argument('name', required = True, help = 'Name cannot be blank.')
post_category_parser.add_argument('limit', type = int, required = True, help = 'Limit cannot be empty.')

delete_category_parser = reqparse.RequestParser()
delete_category_parser.add_argument('id', type = int, required = True)

class CategoriesAPI(Resource):

    def get(self):
        return categories

    def post(self):
        args = post_category_parser.parse_args()
        newCategory = {
            "id": len(categories),
            "name": args["name"],
            "limit": args["limit"]
        }
        categories.append(newCategory)
        return categories[len(categories) - 1], 201

    def delete(self):
        args = delete_category_parser.parse_args()
        for purchase in purchases:
            if purchase['category_id'] == args['id']:
                purchase['category_id'] = 0

        for category in categories:
            if category['id'] == args['id']:
                categories.remove(category)

        return categories[len(categories) - 1], 201



post_purchase_parser = reqparse.RequestParser()
post_purchase_parser.add_argument('description')
post_purchase_parser.add_argument('price', type = int, required = True, help = 'Price cannot be blank.')
post_purchase_parser.add_argument('date', required = True, help = 'Date cannot be blank.')
post_purchase_parser.add_argument('category_id', type = int, required = True)

class PurchasesAPI(Resource):

    def get(self):
        return purchases

    def post(self):
        args = post_purchase_parser.parse_args()
        newPurchase = {
            "description": args["description"],
            "price": args["price"],
            "date": args["date"],
            "category_id": args["category_id"]
        }
        purchases.append(newPurchase)
        print('updated purchases: ' + str(purchases))
        return purchases[len(purchases) - 1], 201




api.add_resource(CategoriesAPI, '/cats')
api.add_resource(PurchasesAPI, '/purchases')


''' -------------------------------Start -------------------------------'''
app.secret_key = 'super-duper-secret'

if __name__ == '__main__':
    app.run()
