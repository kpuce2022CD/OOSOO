from django.db import models

class Movie(models.Model):
    title = models.CharField(max_length=50)
    genre = models.CharField(max_length=30)
    year = models.IntegerField()

    def __str__(self):
        return self.title
