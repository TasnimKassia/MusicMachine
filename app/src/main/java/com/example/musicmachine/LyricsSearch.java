package com.example.musicmachine;

import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.entity.lyrics.Lyrics;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;

import java.util.List;

public class LyricsSearch {
    String apiKey = "1471f2a32786006d6241f7c584b6a0bc";
    MusixMatch musixMatch = new MusixMatch(apiKey);

    public  LyricsSearch(){

    }

    public String MatchSongByLyrics(String lyric) throws MusixMatchException {
        List<Track> tracks = musixMatch.searchTracks( lyric, "", "", 1, 1, true);
//        for testing purposes
        for (Track trk : tracks) {
            TrackData trkData = trk.getTrack();
            int trackID = trkData.getTrackId();
            Lyrics lyrics = musixMatch.getLyrics(trackID);

            System.out.println("Title : "       + trkData.getTrackName());
            System.out.println("Album Name : "  + trkData.getAlbumName());
             System.out.println("Artist Name : " + trkData.getArtistName());
            System.out.println();

            System.out.println("Lyrics ID       : "     + lyrics.getLyricsId());
            System.out.println("Lyrics Language : "     + lyrics.getLyricsLang());
            System.out.println("Lyrics Body     : "     + lyrics.getLyricsBody());

        }
        Track trk = tracks.get(0);
        TrackData trkData = trk.getTrack();

        return trkData.getTrackName();
    }


    public static void main(String[] args) {
        LyricsSearch ls = new LyricsSearch();
        try {
            System.out.println(ls.MatchSongByLyrics(""));
        }catch(Exception e){}
    }
}
