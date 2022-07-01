from api.models import UserInterworking
from api.models import WatchingLog
from api.models import WishList


def deleteInterworking(email, platform):

    list_id = list()

    try:
        interworking = UserInterworking.objects.values().filter(u_email=email, platform=platform)

        for i in interworking:
            if i['platform'] == platform:
                list_id.append(i['i_id'])

        for k in list_id:
            delete_watching = WatchingLog.objects.filter(i_id=k)
            delete_watching.delete()

            delete_wish = WishList.objects.filter(i_id=k)
            delete_wish.delete()

            delete_user_inter = UserInterworking.objects.filter(i_id=k)
            delete_user_inter.delete()

        return "Success"

    except:
        return "Failure"