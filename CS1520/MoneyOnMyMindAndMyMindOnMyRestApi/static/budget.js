let categories
let purchases
let today = new Date()

window.onload = getCategories

/* ============== Handlers ============== */
function getCategories() {
    let req = new XMLHttpRequest()

    if (!req) {
        alert("Error: Unable to create XMLHttp")
        return
    }

    req.onreadystatechange = function() {
        handleCategories(req)
    }

    req.open("GET", "/cats")
    req.send()
}


function handleCategories(req) {
    if (req.readyState == XMLHttpRequest.DONE) {
        if (req.status == 200) {
            categories = JSON.parse(req.responseText)
            console.log(categories)
            getPurchases()
        }
    }
}


function getPurchases() {
    let req = new XMLHttpRequest()

    if (!req) {
        alert("Error: Unable to create XMLHttp")
        return
    }

    req.onreadystatechange = function() {
        handlePurchases(req)
    }

    req.open("GET", "/purchases")
    req.send()
}


function handlePurchases(req) {
    if (req.readyState == XMLHttpRequest.DONE) {
        if (req.status == 200) {
            clearForms()
            purchases = JSON.parse(req.responseText).filter(checkCurrentMonth)
            console.log(purchases)
            categories = categories.map(marryPurchaseToCategory)
            clearCategoryElements()
            categories.forEach(addCategoryElement)
        }
    }
}


function purchasePost() {
    let form = document.getElementById("newPurchase")

    let argText = ""
    argText += "description=" + form.elements["description"].value
    argText += "&price=" + form.elements["price"].value
    argText += "&date=" + form.elements["purchaseDate"].value
    argText += "&category_id=" + form.elements["purchaseCategorySelect"].value

    let req = new XMLHttpRequest()

    if (!req) {
        alert("Error: Unable to create XMLHttp")
        return
    }

    req.onreadystatechange = function() {
        afterHttpRequest(req)
    }

    req.open("POST", "/purchases")
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
    req.send(argText)
}


function categoriesPost() {
    let form = document.getElementById("newCategory")

    let argText = ""
    argText += "name=" + form.elements["categoryName"].value
    argText += "&limit=" + form.elements["limit"].value

    let req = new XMLHttpRequest()

    if (!req) {
        alert("Error: Unable to create XMLHttp")
        return
    }

    req.onreadystatechange = function() {
        afterHttpRequest(req)
    }

    req.open("POST", "/cats")
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
    req.send(argText)
}


function categoriesDelete() {
    let form = document.getElementById("deleteCategory")

    let argText = "id=" + form.elements["category_id"].value

    let req = new XMLHttpRequest()

    if (!req) {
        alert("Error: Unable to create XMLHttp")
        return
    }

    req.onreadystatechange = function() {
        afterHttpRequest(req)
    }

    req.open("DELETE", "/cats")
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
    req.send(argText)
}


function afterHttpRequest(req) {
    if (req.readyState == XMLHttpRequest.DONE) {
        if (req.status == 201 || req.status == 204) {
            console.log(req.responseText)
            getCategories()
        }
    }
}


/* ============== DOMers (These ones dont eat people) ============== */
function addCategoryElement(category) {
    let wrapper = document.getElementById("contentWrapper")

    let categoryElement = document.createElement("div")
    categoryElement.classList.add("category")
    categoryElement.id = "category:" + category.id

    let categoryHeader = document.createElement("h3")
    categoryHeader.classList.add("categoryTitle")
    categoryHeader.textContent = category.name
    categoryElement.appendChild(categoryHeader)

    let content
    if (category.id == 0) {
        content = "You have spent $" + category.spent + " on Uncategorized purchases."
    }
    else {
        if (category.remaining < 0) {
            content = "You have gone $" + Math.abs(category.remaining) + " over budget of $" + category.limit + "."
        }
        else{
            content = "You have $" + category.remaining + "/$" + category.limit + " left in your budget."
        }
    }

    let contentElement = document.createElement("div")
    contentElement.classList.add("categoryContent")
    contentElement.textContent = content
    categoryElement.appendChild(contentElement)

    wrapper.appendChild(categoryElement)

    let optionElement = document.createElement("option")
    optionElement.value = category.id
    optionElement.textContent = category.name
    document.getElementById("purchaseCategorySelect").appendChild(optionElement)

    if (category.id !=  0) {
        optionElement = document.createElement("option")
        optionElement.value = category.id
        optionElement.textContent = category.name
        document.getElementById("deleteCategorySelect").appendChild(optionElement)
    }
}


function clearForms() {
    let forms = Array.prototype.slice.call(document.forms)

    forms.forEach(function(form) {
        let fields = Array.prototype.slice.call(form.getElementsByTagName("input"))

        fields.forEach(function(element) {
            if (element.type != "submit") {
                element.value = null
            }
        })
    })

    let purchaseDate = document.getElementById("purchaseDate")
    purchaseDate.value = today.toISOString().split('T')[0]

    let dropdowns = Array.prototype.slice.call(document.getElementsByTagName("select"))

    dropdowns.forEach(function(dropdown) {
        while (dropdown.hasChildNodes()) {
            dropdown.removeChild(dropdown.lastChild)
        }
    })
}


function clearCategoryElements() {
    let wrapper = document.getElementById("contentWrapper")
    while (wrapper.hasChildNodes()) {
        wrapper.removeChild(wrapper.lastChild)
    }
}


/* ============== Helpers ============== */
function checkCurrentMonth(purchase) {
    let presentDate = new Date(purchase.date)
    let isCurrentMonth = ((presentDate.getUTCMonth() == today.getUTCMonth()) && (presentDate.getUTCFullYear() == today.getUTCFullYear()))
    return isCurrentMonth
}


function marryPurchaseToCategory(category) {
    if (category == undefined) {
        console.log("Undefined category, duh duh duh")
    }

    let updatedCategory = Object.assign({}, category)
    updatedCategory['spent'] = purchases.filter(purchase => purchase.category_id == category.id).map(getPurchasePrice).reduce(accumulatePurchase, 0)
    updatedCategory['remaining'] = updatedCategory.limit - updatedCategory.spent
    return updatedCategory
}


function getPurchasePrice(purchase) {
    if (purchase == undefined) {
        console.log("Undefined purchase received. Get Websters to fix that for ya.")
    }

    return purchase.price
}


function accumulatePurchase(x, y) {
    if (x < 0 || y < 0) {
        console.log("A purchase price cannot be negative. KISS (keep it simple stupid) - Michael Scott")
    }

    return x + y;
}
