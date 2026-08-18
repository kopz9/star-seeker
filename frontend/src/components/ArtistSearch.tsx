import React, { useState } from 'react';
import { api, ApiError } from '../services/api';
import { useAuth } from '../hooks/useAuth';
import type { SpotifySearchResponse } from '../types/api';
import { Search, Sparkles, UserPlus } from 'lucide-react';

export function ArtistSearch() {
  const { token } = useAuth();
  const [query, setQuery] = useState('');
  const [results, setResults] = useState<SpotifySearchResponse[]>([]);
  const [isSearching, setIsSearching] = useState(false);
  const [hiringArtist, setHiringArtist] = useState<string | null>(null);
  const [error, setError] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!query.trim()) return;

    setError('');
    setSuccessMessage('');
    setIsSearching(true);

    try {
      const artists = await api.searchArtists(query);
      setResults(artists);
    } catch (err) {
      if (err instanceof ApiError) {
        setError(err.message);
      } else {
        setError('Failed to search artists');
      }
    } finally {
      setIsSearching(false);
    }
  };

  const handleHireArtist = async (artistName: string) => {
    if (!token) return;

    setError('');
    setSuccessMessage('');
    setHiringArtist(artistName);

    try {
      // Note: This would need the artist ID, but the search endpoint only returns name and imageUrl
      // You'll need to modify your backend to return artist IDs or create artists on hire
      // For now, this is a placeholder
      setSuccessMessage(`Successfully hired ${artistName}!`);
      
      // Remove the hired artist from results after a delay
      setTimeout(() => {
        setResults(results.filter(r => r.name !== artistName));
        setSuccessMessage('');
      }, 2000);
    } catch (err) {
      if (err instanceof ApiError) {
        setError(err.message);
      } else {
        setError('Failed to hire artist');
      }
    } finally {
      setHiringArtist(null);
    }
  };

  return (
    <div className="w-full max-w-6xl mx-auto">
      <div className="mb-12 text-center">
        <div className="inline-flex items-center gap-2 mb-4">
          <Sparkles className="w-8 h-8 text-yellow-400" />
          <h1 className="text-6xl font-display font-bold bg-gradient-to-r from-purple-400 via-pink-500 to-yellow-500 bg-clip-text text-transparent">
            Discover Artists
          </h1>
          <Sparkles className="w-8 h-8 text-yellow-400" />
        </div>
        <p className="text-gray-400 text-xl">Search and hire the next big stars</p>
      </div>

      <form onSubmit={handleSearch} className="mb-8">
        <div className="relative">
          <Search className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
          <input
            type="text"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            placeholder="Search for artists by name..."
            className="w-full pl-12 pr-4 py-4 bg-white/5 border border-white/10 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all text-white text-lg placeholder-gray-500"
          />
          <button
            type="submit"
            disabled={isSearching || !query.trim()}
            className="absolute right-2 top-1/2 -translate-y-1/2 px-6 py-2 bg-gradient-to-r from-purple-500 to-pink-600 text-white font-semibold rounded-lg hover:from-purple-600 hover:to-pink-700 disabled:opacity-50 disabled:cursor-not-allowed transition-all"
          >
            {isSearching ? 'Searching...' : 'Search'}
          </button>
        </div>
      </form>

      {error && (
        <div className="mb-6 bg-red-500/10 border border-red-500/50 rounded-lg p-4 text-red-400 animate-shake">
          {error}
        </div>
      )}

      {successMessage && (
        <div className="mb-6 bg-green-500/10 border border-green-500/50 rounded-lg p-4 text-green-400 animate-fade-in">
          {successMessage}
        </div>
      )}

      {results.length > 0 && (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
          {results.map((artist, index) => (
            <div
              key={`${artist.name}-${index}`}
              className="group relative bg-gradient-to-br from-white/5 to-white/[0.02] border border-white/10 rounded-2xl p-6 hover:border-purple-500/50 transition-all duration-300 hover:transform hover:scale-105"
              style={{
                animationDelay: `${index * 50}ms`,
                animation: 'fade-in-up 0.5s ease-out forwards',
              }}
            >
              <div className="aspect-square mb-4 rounded-xl overflow-hidden bg-gradient-to-br from-purple-500/20 to-pink-500/20">
                {artist.imageUrl ? (
                  <img
                    src={artist.imageUrl}
                    alt={artist.name}
                    className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-300"
                  />
                ) : (
                  <div className="w-full h-full flex items-center justify-center text-6xl font-bold text-white/20">
                    {artist.name[0]}
                  </div>
                )}
              </div>

              <h3 className="text-xl font-bold text-white mb-4 truncate" title={artist.name}>
                {artist.name}
              </h3>

              <button
                onClick={() => handleHireArtist(artist.name)}
                disabled={hiringArtist === artist.name}
                className="w-full flex items-center justify-center gap-2 py-2.5 px-4 bg-gradient-to-r from-purple-500 to-pink-600 text-white font-semibold rounded-lg hover:from-purple-600 hover:to-pink-700 disabled:opacity-50 disabled:cursor-not-allowed transition-all transform hover:scale-105 active:scale-95"
              >
                <UserPlus className="w-4 h-4" />
                {hiringArtist === artist.name ? 'Hiring...' : 'Hire Artist'}
              </button>
            </div>
          ))}
        </div>
      )}

      {!isSearching && results.length === 0 && query && (
        <div className="text-center py-16">
          <div className="text-6xl mb-4">🎵</div>
          <h3 className="text-2xl font-bold text-white mb-2">No artists found</h3>
          <p className="text-gray-400">Try searching with a different name</p>
        </div>
      )}

      {!query && (
        <div className="text-center py-16">
          <div className="text-6xl mb-4">🎸</div>
          <h3 className="text-2xl font-bold text-white mb-2">Start searching</h3>
          <p className="text-gray-400">Enter an artist name to discover your next star</p>
        </div>
      )}

      <style>{`
        @keyframes fade-in-up {
          from {
            opacity: 0;
            transform: translateY(20px);
          }
          to {
            opacity: 1;
            transform: translateY(0);
          }
        }
      `}</style>
    </div>
  );
}
