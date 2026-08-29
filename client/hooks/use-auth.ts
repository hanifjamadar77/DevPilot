"use client";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useRouter } from "next/navigation";

import { api, ApiError } from "@/lib/api";
import { queryKeys } from "@/lib/query-keys";
import path from "path/win32";

export const AUTH_COOKIE = "devpilot_auth";

export function setAuthCookie(authed: boolean) {
  if (typeof document === "undefined") return;
  if (authed) {
    document.cookie = `${AUTH_COOKIE}=1; path=/; max-age=${60 * 60 * 24 * 7}; SameSite=Lax'`;
  } else {
    document.cookie = `${AUTH_COOKIE}=''; path=/; max-age=0; SameSite=Lax'`;
  }
}

export function useCurrentUser() {
  return useQuery({
    queryKey: queryKeys.auth.me(),
    queryFn: async () => {
      try {
        const user = await api.me();
        setAuthCookie(true);
        return user;
      } catch (error) {
        setAuthCookie(false);
        throw error;
      }
    },
  });
}

export function useLogout() {
  const queryClient = useQueryClient();
  const router = useRouter();

  return useMutation({
    mutationFn: async () => {
      await api.logout();
    },
    onSuccess: () => {
      setAuthCookie(false);
      queryClient.invalidateQueries({ queryKey: queryKeys.auth.all });
      router.replace("/login");
    },
  });
}
