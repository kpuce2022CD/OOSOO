from api.models import WatchingLog
from api.models import Contents


def add_watchinglog(log, interworking):
    contents = list()

    for title in log:
        content = Contents.objects.filter(title__startswith=title).values()
        if content.count() != 0 & interworking.count() != 0:
            contents.append(content[0])
            WatchingLog.objects.create(c_id=content[0].id, i_id=interworking[0].id)

    return contents
