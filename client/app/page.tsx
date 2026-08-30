import Link from "next/link";
import {
  ArrowRight,
  FolderGit2,
  MessageSquareCode,
  Sparkles,
} from "lucide-react";

import { DevPilotIcon } from "@/components/icons/devpilot-icon";
import { BrandMark } from "@/components/layout/app-shell";
import { ModeToggle } from "@/components/ui/mode-toggle";
import { buttonVariants } from "@/components/ui/button";
import { getGithubLoginUrl } from "@/lib/api";
import { cn } from "@/lib/utils";

const features = [
  {
    icon: FolderGit2,
    title: "Connect GitHub",
    description:
      "OAuth with repository access for your public and private repositories.",
  },
  {
    icon: Sparkles,
    title: "Index with RAG",
    description:
      "Chunk and embed your code into Postgres with pgvector for intelligent retrieval.",
  },
  {
    icon: MessageSquareCode,
    title: "Ask anything",
    description:
      "Get grounded answers about your codebase with clickable citations.",
  },
];

export default function HomePage() {
  return (
    <div className="relative flex min-h-svh flex-col overflow-hidden bg-background">
      {/* Background gradient */}
      <div className="pointer-events-none absolute inset-0 bg-[radial-gradient(ellipse_at_top,rgba(120,20,70,0.18),transparent_55%)]" />

      {/* Header */}
      <header className="relative z-10 mx-auto flex h-20 w-full max-w-5xl items-center justify-between px-4">
        <BrandMark />

        <div className="flex items-center gap-2">
          <ModeToggle />

          <Link
            href="/login"
            className={cn(
              buttonVariants({
                variant: "ghost",
                size: "sm",
              })
            )}
          >
            Sign in
          </Link>
        </div>
      </header>

      {/* Main Content */}
      <main className="relative z-10 flex flex-1 flex-col items-center px-4 pt-16 pb-10">
        {/* Hero Section */}
        <section className="mx-auto max-w-2xl space-y-6 text-center">
          {/* Logo */}
          <div className="mx-auto flex size-20 items-center justify-center">
            <DevPilotIcon className="size-14 rounded-2xl shadow-lg" />
          </div>

          {/* Heading */}
          <div className="space-y-3">
            <h1 className="font-heading text-4xl font-semibold tracking-tight sm:text-5xl">
              DevPilot
            </h1>

            <p className="mx-auto max-w-xl text-lg leading-relaxed text-muted-foreground">
              Connect GitHub, index any repository, and chat with your
              codebase using retrieval-augmented answers and citations.
            </p>
          </div>

          {/* Buttons */}
          <div className="flex flex-wrap items-center justify-center gap-3">
            <a
              href={getGithubLoginUrl()}
              className={cn(
                buttonVariants({
                  size: "lg",
                }),
                "inline-flex items-center gap-2"
              )}
            >
              <FolderGit2 className="size-4" />

              <span>Continue with GitHub</span>

              <ArrowRight className="size-4" />
            </a>

            <a
              href="#how-it-works"
              className={cn(
                buttonVariants({
                  variant: "outline",
                  size: "lg",
                }),
                "inline-flex items-center gap-2"
              )}
            >
              See how it works
            </a>
          </div>
        </section>

        {/* Feature Cards */}
        <section
          id="how-it-works"
          className="mx-auto mt-14 grid w-full max-w-5xl gap-4 md:grid-cols-3"
        >
          {features.map((feature) => {
            const Icon = feature.icon;

            return (
              <div
                key={feature.title}
                className="rounded-2xl border bg-card/70 p-6 shadow-sm backdrop-blur transition-all hover:-translate-y-1 hover:shadow-md"
              >
                {/* Icon */}
                <div className="mb-5 flex size-10 items-center justify-center rounded-xl bg-muted">
                  <Icon className="size-5" />
                </div>

                {/* Title */}
                <h2 className="text-base font-semibold">
                  {feature.title}
                </h2>

                {/* Description */}
                <p className="mt-2 text-sm leading-relaxed text-muted-foreground">
                  {feature.description}
                </p>
              </div>
            );
          })}
        </section>
      </main>

      {/* Footer */}
      <footer className="relative z-10 border-t">
        <div className="mx-auto flex h-14 max-w-5xl items-center justify-center px-4">
          <p className="text-xs text-muted-foreground">
            © {new Date().getFullYear()} DevPilot. Chat with your code.
          </p>
        </div>
      </footer>
    </div>
  );
}