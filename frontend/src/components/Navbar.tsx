import React from 'react';
import { useAuth } from '../hooks/useAuth';
import { LogOut, User, Star } from 'lucide-react';

export function Navbar() {
  const { user, logout, isAuthenticated } = useAuth();

  if (!isAuthenticated) return null;

  return (
    <nav className="fixed top-0 left-0 right-0 z-50 bg-gray-900/80 backdrop-blur-xl border-b border-white/10">
      <div className="max-w-7xl mx-auto px-6 py-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="p-2 bg-gradient-to-br from-purple-500 to-pink-600 rounded-xl">
              <Star className="w-6 h-6 text-white" />
            </div>
            <h1 className="text-2xl font-display font-bold bg-gradient-to-r from-purple-400 to-pink-500 bg-clip-text text-transparent">
              StarSeeker
            </h1>
          </div>

          <div className="flex items-center gap-4">
            <div className="flex items-center gap-3 px-4 py-2 bg-white/5 rounded-lg border border-white/10">
              <User className="w-5 h-5 text-purple-400" />
              <span className="text-white font-medium">{user?.username}</span>
            </div>

            <button
              onClick={logout}
              className="flex items-center gap-2 px-4 py-2 bg-red-500/10 hover:bg-red-500/20 text-red-400 font-medium rounded-lg border border-red-500/30 hover:border-red-500/50 transition-all"
            >
              <LogOut className="w-4 h-4" />
              Logout
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
}
