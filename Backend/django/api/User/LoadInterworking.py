from api.models import UserInterworking


def load_interworking(email):
    interworking = UserInterworking.objects.filter(u_email=email)
    print(interworking)

    return interworking
