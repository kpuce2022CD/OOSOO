from api.models import Users

def deleteUser(email):

    item = Users.objects.get(email=email)
    item.delete()

    return True