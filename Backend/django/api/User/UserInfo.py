from api.models import Users


def userinfo(email):
    user = Users.objects.filter(email=email)

    return user.values()[0]
