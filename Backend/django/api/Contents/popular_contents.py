import requests
import json

from api.models import Contents

def popular_contents():
    popular_list = []

    for i in range(1, 15):
        response = requests.get(
            "https://api.themoviedb.org/3/movie/popular?api_key=1e7509b4953de9d2a24a16ebe05fba43&language=ko-KR&page={}".format(i))
        popular_json = json.loads(response.text)
        popular_result = popular_json["results"]

        for content in popular_result:
            id = 'm_'+str(content['id'])
            contentInfo = Contents.objects.filter(id=id).values()
            if contentInfo:
                popular_list.append(contentInfo[0])

        response = requests.get(
            "https://api.themoviedb.org/3/tv/popular?api_key=1e7509b4953de9d2a24a16ebe05fba43&language=ko-KR&page={}".format(
                i))
        popular_json = json.loads(response.text)
        popular_result = popular_json["results"]

        for content in popular_result:
            id = 't_' + str(content['id'])
            contentInfo = Contents.objects.filter(id=id).values()
            if contentInfo:
                popular_list.append(contentInfo[0])


    return popular_list
