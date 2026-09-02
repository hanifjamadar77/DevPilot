"use client";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useRouter } from "next/navigation";

import { api } from "@/lib/api";
import { queryKeys } from "@/lib/query-keys";

export const AUTH_COOKIE = "devpilot_auth";

export function setAuthCookie(authed: boolean) {
  if (typeof document === "undefined") return;

  if (authed) {
    document.cookie = `${AUTH_COOKIE}=1; path=/; max-age=${60 * 60 * 24 * 7}; SameSite=Lax`;
    return;
  }

  document.cookie = `${AUTH_COOKIE}=; path=/; max-age=0; SameSite=Lax; expires=Thu, 01 Jan 1970 00:00:00 GMT`;
}

export function useCurrentUser(options?: { enabled?: boolean }) {
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
    enabled: options?.enabled !== false,
  });
}

export function useLogout() {
  const queryClient = useQueryClient();
  const router = useRouter();

  return useMutation({
    mutationFn: async () => {
      try {
        await api.logout();
      } catch (error) {
        console.warn("Logout request failed; clearing local auth anyway.", error);
      }
    },
    onSettled: () => {
      setAuthCookie(false);
      queryClient.setQueryData(queryKeys.auth.me(), null);
      queryClient.invalidateQueries({ queryKey: queryKeys.auth.all });
      router.replace("/login");
      router.refresh();
    },
  });
}
