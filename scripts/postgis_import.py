import json
import geojson
import psycopg2

conn = psycopg2.connect("dbname=brdr user=mruane")
cur= conn.cursor()

with open('ireland-counties.json') as file:
    gj=geojson.load(file)
    for feature in gj['features']:
        name=feature['properties']['name']
        geo=(json.dumps(feature['geometry']))
        cur.execute('INSERT INTO geo_locations (name, geo) VALUES (%s, ST_GeomFromText(ST_AsText(ST_GeomFromGeoJSON(%s))))', (name, geo))

conn.commit()
cur.close()
conn.close()