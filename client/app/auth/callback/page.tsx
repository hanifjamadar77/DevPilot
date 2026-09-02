import { Suspense } from "react";
import { Spinner } from "@/components/ui/spinner";
import { AuthCallbackContent } from "./auth-callback-content";

// Disable static generation - this page is dynamic
export const dynamic = "force-dynamic";

export default function AuthCallbackPage() {
  return (
    <Suspense
      fallback={
        <div className="flex min-h-svh flex-col items-center justify-center gap-3">
          <Spinner className="size-6" />
          <p className="text-sm text-muted-foreground">Finishing GitHub sign-in ...</p>
        </div>
      }
    >
      <AuthCallbackContent />
    </Suspense>
  );
}