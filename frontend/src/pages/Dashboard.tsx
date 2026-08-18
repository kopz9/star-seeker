import React from 'react';
import { ArtistSearch } from '../components/ArtistSearch';
import { Navbar } from '../components/Navbar';

export function Dashboard() {
  return (
    <div className="min-h-screen bg-gray-900">
      <Navbar />
      
      {/* Background effects */}
      <div className="fixed inset-0 overflow-hidden pointer-events-none">
        <div className="absolute top-0 left-1/4 w-96 h-96 bg-purple-500/5 rounded-full blur-3xl" />
        <div className="absolute bottom-0 right-1/4 w-96 h-96 bg-pink-500/5 rounded-full blur-3xl" />
      </div>

      <main className="relative pt-24 pb-12 px-6">
        <ArtistSearch />
      </main>
    </div>
  );
}
