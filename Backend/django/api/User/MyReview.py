from api.models import Contents
from api.models import ContentsReview

def MyReview(email):
    list_review = list()

    review = ContentsReview.objects.filter(u_email=email).values()

    for re in review:
        content = Contents.objects.filter(id=re['c_id_id']).values()
        list_review.append({'review': re, 'title': content})

    return list_review
