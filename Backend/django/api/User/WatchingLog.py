from api.User.LoadInterworking import load_interworking
from api.models import WatchingLog
from api.models import Contents


def watchinglog(email):
    contents = list()

    interworking = load_interworking(email)

    for user in interworking:
        print(user.i_id)
        watchings = WatchingLog.objects.filter(i_id=user.i_id)

        print(watchings.values())

        for watching in watchings:
            print(watching.c_id)
            content = Contents.objects.filter(id=watching.c_id.id).values()[0]
            contents.append(content)

    return contents
