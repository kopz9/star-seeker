import React, { useState, useEffect } from 'react';
import { api, ApiError } from '../services/api';
import { useAuth } from '../hooks/useAuth';
import type { ContractResponse } from '../types/api';
import { Trash2 } from 'lucide-react';

export function MyContracts() {
  const { token } = useAuth();
  const [contracts, setContracts] = useState<ContractResponse[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');
  const [deletingId, setDeletingId] = useState<number | null>(null);

  useEffect(() => {
    if (!token) return;

    const fetchContracts = async () => {
      setIsLoading(true);
      setError('');
      try {
        const data = await api.getMyContracts(token);
        setContracts(data);
      } catch (err) {
        if (err instanceof ApiError) {
          setError(err.message);
        } else {
          setError('Failed to load your artists');
        }
      } finally {
        setIsLoading(false);
      }
    };

    fetchContracts();
  }, [token]);

  const handleDelete = async (contract: ContractResponse) => {
    if (!token) return;

    const confirmed = window.confirm(
      `Are you sure you want to remove ${contract.artistName}? This cannot be undone.`
    );

    if(!confirmed) return;

    setDeletingId(contract.id);
    try {
      await api.deleteContract(contract.id, token);
      setContracts(contracts.filter(c => c.id !== contract.id));
    } catch (err) {
      if (err instanceof ApiError) {
        setError(err.message);
      } else {
        setError('Failed to remove artist');
      }
    } finally {
      setDeletingId(null);
    }
  };

  if (isLoading) {
    return <div className="text-center py-16 text-gray-400">Loading your artists...</div>;
  }

  return (
    <div className="w-full max-w-6xl mx-auto">
      <h1 className="text-4xl font-bold text-white mb-8">My Artists</h1>

      {error && (
        <div className="mb-6 bg-red-500/10 border border-red-500/50 rounded-lg p-4 text-red-400">
          {error}
        </div>
      )}

      {contracts.length === 0 ? (
        <div className="text-center py-16">
          <h3 className="text-2xl font-bold text-white mb-2">No artists hired yet</h3>
          <p className="text-gray-400">Search for artists and hire them to see them here</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
          {contracts.map((contract) => (
            <div
              key={contract.id}
              className="group relative bg-gradient-to-br from-white/5 to-white/[0.02] border border-white/10 rounded-2xl p-6"
            >
              <div className="aspect-square mb-4 rounded-xl overflow-hidden bg-gradient-to-br from-purple-500/20 to-pink-500/20">
                {contract.artistImageUrl ? (
                  <img
                    src={contract.artistImageUrl}
                    alt={contract.artistName}
                    className="w-full h-full object-cover"
                  />
                ) : (
                  <div className="w-full h-full flex items-center justify-center text-6xl font-bold text-white/20">
                    {contract.artistName[0]}
                  </div>
                )}
              </div>

              <h3 className="text-xl font-bold text-white mb-4 truncate" title={contract.artistName}>
                {contract.artistName}
              </h3>

              <button
                onClick={() => handleDelete(contract)}
                disabled={deletingId === contract.id}
                className="w-full flex items-center justify-center gap-2 py-2.5 px-4 bg-red-500/10 text-red-400 font-semibold rounded-lg hover:bg-red-500/20 disabled:opacity-50 transition-all"
              >
                <Trash2 className="w-4 h-4" />
                {deletingId === contract.id ? 'Removing...' : 'Remove'}
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}