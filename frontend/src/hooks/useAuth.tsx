import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { api, ApiError } from '../services/api';
import type { User, AuthContextType } from '../types/api';

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // Load auth data from localStorage on mount
    const storedToken = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');

    if (storedToken && storedUser) {
      setToken(storedToken);
      setUser(JSON.parse(storedUser));
    }
    setIsLoading(false);
  }, []);

  const login = async (username: string, password: string): Promise<void> => {
    try {
      const response = await api.login({ username, password });
      
      const userData: User = {
        id: response.id,
        username,
        email: '', // Not returned by login endpoint
      };

      setToken(response.accessToken);
      setUser(userData);
      
      localStorage.setItem('token', response.accessToken);
      localStorage.setItem('user', JSON.stringify(userData));
    } catch (error) {
      if (error instanceof ApiError) {
        throw error;
      }
      throw new Error('Login failed');
    }
  };

  const register = async (username: string, email: string, password: string): Promise<void> => {
    try {
      const userData = await api.register({ username, email, password });
      
      // After registration, automatically log in
      await login(username, password);
    } catch (error) {
      if (error instanceof ApiError) {
        throw error;
      }
      throw new Error('Registration failed');
    }
  };

  const logout = () => {
    setToken(null);
    setUser(null);
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  };

  const value: AuthContextType = {
    user,
    token,
    login,
    register,
    logout,
    isAuthenticated: !!token,
    isLoading,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth(): AuthContextType {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}
