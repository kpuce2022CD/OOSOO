from api.models import UserInterworking

def MyInterworking(email):
    interworking = UserInterworking.objects.values_list('platform', flat=True).filter(u_email=email).order_by('platform')

    return interworking
