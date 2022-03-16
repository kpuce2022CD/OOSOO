from api.User.LoadInterworking import load_interworking
from api.models import WatchingLog
from api.models import Contents


def add_watchinglog(log):
    contents = list()

    for title in log:
        content = Contents.objects.filter(title__startswith=title).values()
        if content.count() != 0:
            contents.append(content[0])

    return contents
