from api.models import Contents
from api.models import ContentsReview
import pandas as pd


def unseen_movies(email):
    contents_id = list()

    contents = Contents.objects.filter(_type='movie').values()
    review = ContentsReview.objects.filter(u_email=email)

    for seen in review:
        print(seen.c_id)
        contents_id.append(seen.c_id.id)

    seen_contents = Contents.objects.filter(id__in=contents_id).values()

    contents_df = pd.DataFrame(list(contents))
    seen_contents_df = pd.DataFrame(list(seen_contents))
    print(seen_contents_df)

    unseen_contents_df = pd.concat([contents_df, seen_contents_df]).drop_duplicates(keep=False)
    print(unseen_contents_df)

    unseen_tmdbid = unseen_contents_df['id'].to_frame()
    unseen_tmdbid['tmdbId'] = unseen_tmdbid.id.str.split('_').str[1]
    unseen_tmdbid = unseen_tmdbid['tmdbId'].to_frame()
    print(unseen_tmdbid)

    return unseen_tmdbid.to_dict('list')['tmdbId']
