from api.models import WatchingLog
from api.models import Contents


def add_watchinglog(log, interworking):
    contents = list()

    for title in log:
        content = Contents.objects.filter(title__startswith=title)

        if content.values().count() != 0:
            exist = WatchingLog.objects.filter(c_id=content[0])
            exist = exist.filter(i_id=interworking)

            if exist.values().count() == 0:
                contents.append(content.values()[0])
                WatchingLog.objects.create(c_id=content[0], i_id=interworking)

    return contents
