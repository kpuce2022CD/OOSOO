# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Contents(models.Model):
    id = models.CharField(primary_key=True, max_length=100)
    field_type = models.CharField(db_column='_type', max_length=10)  # Field renamed because it started with '_'.
    title = models.CharField(max_length=100)
    genre = models.CharField(max_length=100)
    production_countries = models.CharField(max_length=200)
    vote_count = models.IntegerField()
    vote_average = models.FloatField()
    number_of_seasons = models.IntegerField(blank=True, null=True)
    number_of_episodes = models.IntegerField(blank=True, null=True)
    release_date = models.DateField(blank=True, null=True)
    adult = models.IntegerField(blank=True, null=True)
    poster_path = models.CharField(max_length=200, blank=True, null=True)
    runtime = models.IntegerField(blank=True, null=True)
    overview = models.CharField(max_length=2000, blank=True, null=True)
    now_status = models.CharField(max_length=100, blank=True, null=True)
    flatrate = models.CharField(max_length=100, blank=True, null=True)
    rent = models.CharField(max_length=100, blank=True, null=True)
    buy = models.CharField(max_length=100, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'contents'


class ContentsEpisodes(models.Model):
    field_id = models.CharField(db_column='_id', primary_key=True, max_length=100)  # Field renamed because it started with '_'.
    c = models.ForeignKey(Contents, models.DO_NOTHING)
    season_num = models.IntegerField()
    title = models.CharField(max_length=100)
    number = models.IntegerField(db_column='NUMBER')  # Field name made lowercase.
    air_date = models.DateField(blank=True, null=True)
    vote_count = models.IntegerField(blank=True, null=True)
    vote_average = models.FloatField(blank=True, null=True)
    overview = models.CharField(max_length=2000, blank=True, null=True)
    still_path = models.CharField(max_length=200, blank=True, null=True)
    crew = models.CharField(max_length=1000, blank=True, null=True)
    guests = models.CharField(max_length=1000, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'contents_episodes'


class ContentsReview(models.Model):
    id = models.IntegerField(primary_key=True)
    c = models.ForeignKey(Contents, models.DO_NOTHING)
    u = models.ForeignKey('Users', models.DO_NOTHING)
    field_like = models.IntegerField(db_column='_like')  # Field renamed because it started with '_'.
    rating = models.FloatField()
    review = models.CharField(max_length=1000)
    field_datetime = models.DateTimeField(db_column='_datetime')  # Field renamed because it started with '_'.

    class Meta:
        managed = False
        db_table = 'contents_review'


class ContentsSeasons(models.Model):
    field_id = models.CharField(db_column='_id', primary_key=True, max_length=100)  # Field renamed because it started with '_'.
    c = models.ForeignKey(Contents, models.DO_NOTHING)
    air_date = models.DateField()
    title = models.CharField(max_length=100)
    number = models.IntegerField(db_column='NUMBER')  # Field name made lowercase.
    overview = models.CharField(max_length=2000, blank=True, null=True)
    poster_path = models.CharField(max_length=200, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'contents_seasons'


class UserInterworking(models.Model):
    i_id = models.CharField(primary_key=True, max_length=50)
    u = models.ForeignKey('Users', models.DO_NOTHING)
    platform = models.CharField(max_length=20)
    id = models.CharField(max_length=50)
    passwd = models.CharField(max_length=20)
    profile_name = models.CharField(max_length=20)
    expiration_date = models.DateField()
    simple_login = models.CharField(max_length=20, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'user_interworking'


class Users(models.Model):
    id = models.IntegerField(primary_key=True)
    email = models.CharField(max_length=50)
    passwd = models.CharField(max_length=20)
    name = models.CharField(max_length=20)
    phone_number = models.CharField(max_length=20)
    nickname = models.CharField(max_length=20)
    gender = models.IntegerField()
    age = models.IntegerField()
    job = models.CharField(max_length=20, blank=True, null=True)
    overview = models.CharField(max_length=200, blank=True, null=True)
    coin = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'users'


class WatchingLog(models.Model):
    id = models.CharField(primary_key=True, max_length=100)
    c = models.ForeignKey(Contents, models.DO_NOTHING)
    i = models.ForeignKey(UserInterworking, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'watching_log'


class WishList(models.Model):
    id = models.CharField(primary_key=True, max_length=100)
    c = models.ForeignKey(Contents, models.DO_NOTHING)
    i = models.ForeignKey(UserInterworking, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'wish_list'
