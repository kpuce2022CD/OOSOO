from api.User.LoadInterworking import load_interworking
from api.models import WishList
from api.models import Contents


def add_wishlist(wishlist):
    contents = list()

    for title in wishlist:
        content = Contents.objects.filter(title__contains=title).values()
        if content.count() != 0:
            contents.append(content[0])

    return contents
