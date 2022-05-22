from api.models import Contents

def Contents_link(c_id):
    value = Contents.objects.filter(id=c_id).values('link')
    for i in value:
        link = i['link']
    return link
